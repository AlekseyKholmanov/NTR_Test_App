package com.example.ntr_test_app.useCases

import com.example.ntr_test_app.models.network.toItem
import com.example.ntr_test_app.models.ui.CountyItem
import com.example.ntr_test_app.models.ui.NetworkResult
import com.example.ntr_test_app.models.ui.StateDetailsItem
import com.example.ntr_test_app.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchStateDetailsUseCaseImpl(
    private val api: Api
) : FetchStateDetailsUseCase {

    override suspend fun getStateDetails(url: String): NetworkResult<List<CountyItem>> = withContext(Dispatchers.IO) {
        val response = api.getStateDetails(url)
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