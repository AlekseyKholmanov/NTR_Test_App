package com.example.ntr_test_app.ui.recycler.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.ntr_test_app.databinding.ItemStateDetailsTableBinding
import com.example.ntr_test_app.extension.toggleGone
import com.example.ntr_test_app.models.ui.StateDetailsTableItem

class StateDetailsTableViewHolder(private val binding: ItemStateDetailsTableBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setState(item: StateDetailsTableItem) {
        with(binding) {
            state.text = item.stateName
            people.text = item.people.toString()
            counties.text = item.counties.toString()

            calculatedGroup.toggleGone(item.calculatedPeople != null)
            calculatedCounties.text = item.calculatedPeople.toString()

            equalInfo.toggleGone(item.isPopulationInfoTruthful != null)
            equalInfo.text = if (item.isPopulationInfoTruthful == true)
                "population equals" else " populations not equals"
        }
    }
}