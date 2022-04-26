package com.example.ntr_test_app.models.network

import com.google.gson.annotations.SerializedName

class BaseResponse<out T>(

    @SerializedName("data")
    val data: List<T>
)
