package com.example.ntr_test_app.models.ui

import com.example.ntr_test_app.R

data class StateDetailsTableItem(
    val stateName: String,
    val people: Int,
    val counties: Int,
    val calculatedPeople: Int? = null,
    val isPopulationInfoTruthful: Boolean? = null,
): Item {
    override fun getViewType(): Int = R.layout.item_state_details_table
}