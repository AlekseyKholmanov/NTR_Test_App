package com.example.ntr_test_app.ui.recycler.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.ntr_test_app.databinding.ItemStateDetailsBinding
import com.example.ntr_test_app.extension.toggleVisibility
import com.example.ntr_test_app.models.ui.StateDetailsItem

class StateDetailsViewHolder(private val binding: ItemStateDetailsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setState(item: StateDetailsItem) {
        with(binding) {
            loadingInfo.toggleVisibility(item.isLoading.not())
            loader.toggleVisibility(item.isLoading)
            stateName.text = item.stateName
            people.text = item.people.toString()
            countries.text = item.counties.toString()
            item.calculatedPeople?.let { calculatedPeople.text = it.toString() }
            item.isPopulationInfoTruthful?.let {
                equalInfo.text = if (it) "population equals" else " populations not equals"
            }
        }
    }
}