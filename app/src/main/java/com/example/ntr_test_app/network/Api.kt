package com.example.ntr_test_app.network

import com.example.ntr_test_app.models.network.BaseResponse
import com.example.ntr_test_app.models.network.StateDetailsResponse
import com.example.ntr_test_app.models.network.StatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface Api {

    @GET("http://pos.idtretailsolutions.com/countytest/states")
    suspend fun getStates(): Response<BaseResponse<StatesResponse>>


    @GET
    suspend fun getStateDetails(@Url url: String): Response<BaseResponse<StateDetailsResponse>>
}