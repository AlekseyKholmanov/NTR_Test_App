package com.example.ntr_test_app.models.network

import com.example.ntr_test_app.models.ui.CountyItem
import com.google.gson.annotations.SerializedName

class StateDetailsResponse(

    @SerializedName("county")
    val country: String,

    @SerializedName("population")
    val population: Int
)

fun StateDetailsResponse.toItem(): CountyItem {
    return CountyItem(
        county = country,
        population = population
    )
}