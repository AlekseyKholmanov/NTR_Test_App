package com.example.ntr_test_app.useCases

import com.example.ntr_test_app.models.ui.CountyItem
import com.example.ntr_test_app.models.ui.NetworkResult
import com.example.ntr_test_app.models.ui.StateDetailsItem

interface FetchStateDetailsUseCase {


    // In my work i prefer return DTO object with data what we need, but in this test we have only number, we don't have database to save it
    suspend fun getStateDetails(url:String): NetworkResult<List<CountyItem>>
}