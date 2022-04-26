package com.example.ntr_test_app.ui.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.ntr_test_app.models.ui.*

class DiffUtils(val oldItems: List<Item>, val newItems: List<Item>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]
        return when {
            old is StateItem && new is StateItem -> true
            old is StateDetailsItem && new is StateDetailsItem -> true
            old is CountyItem && new is CountyItem -> true
            old is OpenDetailsItem && new is OpenDetailsItem -> true
            old is LoaderItem && new is LoaderItem -> true
            old is StateDetailsTableItem && new is StateDetailsItem -> true
            else -> false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]
        return when {
            old is StateItem && new is StateItem -> {
                old == new
            }
            old is StateDetailsItem && new is StateDetailsItem -> {
                old == new
            }
            old is CountyItem && new is CountyItem -> {
                old == new
            }
            old is StateDetailsTableItem && new is StateDetailsTableItem -> {
                old == new
            }
            old is OpenDetailsItem && new is OpenDetailsItem -> true
            old is LoaderItem && new is LoaderItem -> true
            else -> false
        }
    }


}