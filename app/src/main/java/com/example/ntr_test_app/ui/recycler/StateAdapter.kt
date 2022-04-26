package com.example.ntr_test_app.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ntr_test_app.DoubleTapListener
import com.example.ntr_test_app.R
import com.example.ntr_test_app.databinding.*
import com.example.ntr_test_app.models.ui.*
import com.example.ntr_test_app.ui.recycler.diff.DiffUtils
import com.example.ntr_test_app.ui.recycler.holders.*

class StateAdapter(
    private val stateItemCallback: StateItemCallback? = null,
    private val openDetailsCallback: OpenDetailsCallback? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_state -> {
                val binding =
                    ItemStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                val holder = StateViewHolder(binding)

                val doubleTapListener = DoubleTapListener(parent.context,
                    doOnDoubleTap = {
                        val position = holder.bindingAdapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            val data = items[holder.bindingAdapterPosition] as StateItem
                            stateItemCallback?.onDoubleClicked(data)
                        }
                    },
                    doOnSingleTap = {
                        val position = holder.bindingAdapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            val data = items[holder.bindingAdapterPosition] as StateItem
                            stateItemCallback?.onSingleClicked(data)
                        }
                    })

                binding.root.setOnTouchListener(doubleTapListener)
                holder
            }
            R.layout.item_state_details -> {
                val binding = ItemStateDetailsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                StateDetailsViewHolder(binding)
            }
            R.layout.item_open_details -> {
                val binding = ItemOpenDetailsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                binding.openDetails.setOnClickListener {
                    openDetailsCallback?.onClick()
                }
                OpenDetailsViewHolder(binding)
            }
            R.layout.item_loader -> {

                val binding = ItemLoaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                LoaderViewHolder(binding)
            }
            R.layout.item_county -> {
                val binding = ItemCountyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CountiesViewHolder(binding)
            }
            R.layout.item_state_details_table -> {
                val binding = ItemStateDetailsTableBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                StateDetailsTableViewHolder(binding)
            }
            else -> {
                throw RuntimeException("wrong viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val state = items[position]
        when (holder) {
            is StateViewHolder -> {
                holder.setState(state as StateItem)
            }
            is StateDetailsViewHolder -> {
                holder.setState(state as StateDetailsItem)
            }
            is CountiesViewHolder -> {
                holder.setState(state as CountyItem)
            }
            is StateDetailsTableViewHolder -> {
                holder.setState(state as StateDetailsTableItem)
            }
            else -> {

            }
        }
    }

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    override fun getItemCount(): Int = items.size

    fun setItems(numbers: List<Item>) {
        val callback = DiffUtils(oldItems = items, newItems = numbers)
        val result = DiffUtil.calculateDiff(callback)
        items.clear()
        items.addAll(numbers)
        result.dispatchUpdatesTo(this)
    }


}
