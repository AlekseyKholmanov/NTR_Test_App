package com.example.ntr_test_app.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ntr_test_app.models.ui.Item
import com.example.ntr_test_app.models.ui.NetworkResult
import com.example.ntr_test_app.models.ui.StateItem
import com.example.ntr_test_app.useCases.FetchStatesUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StateViewModel(
    private val fetchStatesUseCase: FetchStatesUseCase
) : ViewModel() {

    val userIntent = Channel<Wish>(Channel.UNLIMITED)

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State>
        get() = _state

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        println("CoroutineExceptionHandler got $exception")
        Log.e("M_APP", "exc: ${exception}")
        _state.value = _state.value.copy(
            isLoading = false,
            isError = true
        )
    }

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeEach { wish ->
                Log.d("M_APP","wish: ${wish::class.simpleName}")
                when (wish) {
                    Wish.FetchNumbers -> {
                        _state.value = _state.value.copy(isLoading = true, isError = false)
                        viewModelScope.launch(errorHandler) {
                            val states = fetchStatesUseCase.getStates()
                            when (states) {
                                is NetworkResult.Error -> {
                                    _state.value =
                                        _state.value.copy(isLoading = false, isError = true)
                                }
                                is NetworkResult.Success -> {
                                    _state.value =
                                        _state.value.copy(
                                            isLoading = false,
                                            isError = false,
                                            items = states.data
                                        )
                                }
                            }
                        }
                    }
                    is Wish.OnDoubleClicked -> {
                        val current = _state.value.highlightedItems
                        if (current.contains(wish.state)) {
                            current.remove(wish.state)
                        } else {
                            current.add(wish.state)
                        }
                        val newValues = _state.value.items.map {
                            it.copy(isSelected = current.contains(it.state))
                        }
                        _state.value = _state.value.copy(highlightedItems = current, items = newValues)
                    }
                    is Wish.OnSingleClicked -> {
                        val item = _state.value.items.find { it.state == wish.state } ?: run {
                            Log.e("M_A_APP", "wrong item in list")
                            throw RuntimeException("")
                        }
                        _state.value = _state.value.copy(detailsItems = listOf(item))

                    }
                    is Wish.Filter -> {
                        _state.value = _state.value.copy(currentFilter = wish.filterValue)
                    }
                }
            }
        }
    }


    sealed class Wish {
        object FetchNumbers : Wish()
        class OnDoubleClicked(val state: String) : Wish()
        class OnSingleClicked(val state: String) : Wish()
        class Filter(val filterValue: String) : Wish()
    }
}



data class State(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val items: List<StateItem> = listOf(),
    val currentFilter: String = "",
    val detailsItems: List<Item> = listOf(),
    val highlightedItems: HashSet<String> = hashSetOf()
) {
    fun getFilteredItems(): List<StateItem> {
        return if (currentFilter.isEmpty()) {
            items
        } else {
            items.filter {
                it.state.lowercase().contains(currentFilter)
            }
        }
    }

}