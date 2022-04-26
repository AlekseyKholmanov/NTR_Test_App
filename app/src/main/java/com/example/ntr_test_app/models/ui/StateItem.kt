package com.example.ntr_test_app.models.ui

import com.example.ntr_test_app.R

data class StateItem(
    val state: String,

    val population: Int,

    val counties: Int,

    val detail: String,

    val isSelected: Boolean = false
) : Item {
    override fun getViewType(): Int = R.layout.item_state
}
