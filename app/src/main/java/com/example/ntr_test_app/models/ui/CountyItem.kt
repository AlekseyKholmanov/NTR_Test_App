package com.example.ntr_test_app.models.ui

import com.example.ntr_test_app.R

data class CountyItem(
    val county: String,
    val population: Int
): Item{
    override fun getViewType(): Int = R.layout.item_county

}
