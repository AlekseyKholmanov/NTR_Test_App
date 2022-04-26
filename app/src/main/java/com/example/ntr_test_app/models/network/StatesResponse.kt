package com.example.ntr_test_app.models.network

import com.example.ntr_test_app.models.ui.StateItem
import com.google.gson.annotations.SerializedName

class StatesResponse(

    @SerializedName("state")
    val state: String,

    @SerializedName("population")
    val population: Int,

    @SerializedName("counties")
    val countries: Int,

    @SerializedName("detail")
    val detail: String
)


fun StatesResponse.toItem():StateItem{
    return StateItem(
        state = state,
        population = population,
        counties = countries,
        detail = detail
    )
}