package com.example.ntr_test_app.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ntr_test_app.models.ui.*
import com.example.ntr_test_app.useCases.FetchStateDetailsUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StateDetailsViewModel(
    private val fetchStateDetailsUseCase: FetchStateDetailsUseCase
) : ViewModel(
) {
    val userIntent = Channel<Wish>(Channel.UNLIMITED)

    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("M_APP", "exc: ${exception}")
    }
    private val worker = SupervisorJob() + Dispatchers.IO

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeEach { wish ->
                when (wish) {
                    is Wish.OnSingleClicked -> {
                        worker.cancelChildren()
                        val initDetailsItem = StateDetailsItem(
                            stateName = wish.state.state,
                            people = wish.state.population,
                            counties = wish.state.counties,
                            isLoading = true
                        )
                        val initDetailTableItem = StateDetailsTableItem(
                            stateName = wish.state.state,
                            people = wish.state.population,
                            counties = wish.state.counties,
                        )

                        _state.value = _state.value.copy(
                            stateItems = listOf(initDetailTableItem, OpenDetailsItem, LoaderItem),
                            detailsItems = listOf(initDetailsItem, LoaderItem),
                            isLoading = true
                        )
                        viewModelScope.launch(errorHandler) {
                            withContext(worker) {
                                delay(5000)
                                when (val result =
                                    fetchStateDetailsUseCase.getStateDetails(wish.state.detail)) {
                                    is NetworkResult.Error -> {

                                    }
                                    is NetworkResult.Success -> {
                                        val certainPopulation = result.data.sumOf { it.population }
                                        val isEqual =
                                            certainPopulation == wish.state.population
                                        val detailsItems = StateDetailsItem(
                                            stateName = wish.state.state,
                                            people = wish.state.population,
                                            counties = wish.state.counties,
                                            isLoading = false,
                                            calculatedPeople = certainPopulation,
                                            isPopulationInfoTruthful = isEqual
                                        )

                                        val detailTableItem = StateDetailsTableItem(
                                            stateName = wish.state.state,
                                            people = wish.state.population,
                                            counties = wish.state.counties,
                                            calculatedPeople = certainPopulation,
                                            isPopulationInfoTruthful = isEqual
                                        )

                                        val tableList = mutableListOf(detailTableItem, OpenDetailsItem)
                                        tableList.addAll(result.data)

                                        val detailsResult = mutableListOf<Item>(detailsItems)
                                        detailsResult.addAll(result.data)
                                        _state.value = _state.value.copy(
                                            detailsItems = detailsResult,
                                            stateItems = tableList
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    sealed class Wish {
        class OnSingleClicked(val state: StateItem) : Wish()
    }
}

data class DetailsState(
    val stateItems: List<Item> = listOf(),
    val detailsItems: List<Item> = listOf(),
    val isLoading: Boolean = false
) {
    fun getFilteredItem() = detailsItems.filterNot { it is OpenDetailsItem || it is StateItem }
}

