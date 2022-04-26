package com.example.ntr_test_app.ui.recycler.holders

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.example.ntr_test_app.databinding.ItemStateBinding
import com.example.ntr_test_app.models.ui.StateItem

class StateViewHolder(
    private val binding: ItemStateBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun setState(item: StateItem) {
        with(binding) {
            state.text = item.state
            if (item.isSelected) {
                root.setBackgroundColor(Color.GRAY)
            } else {
                root.setBackgroundColor(Color.WHITE)
            }
        }
    }
}

interface StateItemCallback{
    fun onDoubleClicked(stateItem: StateItem)
    fun onSingleClicked(stateItem: StateItem)
}