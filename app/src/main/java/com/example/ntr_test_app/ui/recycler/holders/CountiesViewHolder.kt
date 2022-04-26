package com.example.ntr_test_app.ui.recycler.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.ntr_test_app.databinding.ItemCountyBinding
import com.example.ntr_test_app.models.ui.CountyItem

class CountiesViewHolder(
    private val binding: ItemCountyBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setState(item: CountyItem) {
        with(binding) {
            county.text = item.county
            people.text = item.population.toString()
        }
    }
}
