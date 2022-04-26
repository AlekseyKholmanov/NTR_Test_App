package com.example.ntr_test_app.useCases

import com.example.ntr_test_app.models.ui.NetworkResult
import com.example.ntr_test_app.models.ui.StateItem

interface FetchStatesUseCase {


    // In my work i prefer return DTO object with data what we need, but in this test we have only number, we don't have database to save it
    suspend fun getStates(): NetworkResult<List<StateItem>>
}