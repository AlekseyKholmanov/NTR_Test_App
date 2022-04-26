package com.example.ntr_test_app.useCases

import com.example.ntr_test_app.models.network.toItem
import com.example.ntr_test_app.models.ui.NetworkResult
import com.example.ntr_test_app.models.ui.StateItem
import com.example.ntr_test_app.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchStatesUseCaseImpl(
    private val api: Api
) : FetchStatesUseCase {
    override suspend fun getStates(): NetworkResult<List<StateItem>> =
        withContext(Dispatchers.IO) {
            val response = api.getStates()
            val data = response.body()?.data
            return@withContext if (response.isSuccessful && data != null) {
                val mapper = data.map { it.toItem() }
                NetworkResult.Success(mapper)
            } else {
                //There we can handle error and pass correct message
                NetworkResult.Error("")
            }
        }
}