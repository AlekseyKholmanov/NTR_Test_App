package com.example.ntr_test_app.ui.recycler.holders

import androidx.recyclerview.widget.RecyclerView
import com.example.ntr_test_app.databinding.ItemOpenDetailsBinding

class OpenDetailsViewHolder(binding: ItemOpenDetailsBinding) :
    RecyclerView.ViewHolder(binding.root)

interface OpenDetailsCallback{
    fun onClick()
}