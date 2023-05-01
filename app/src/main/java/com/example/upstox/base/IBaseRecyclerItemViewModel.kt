package com.example.upstox.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class IBaseRecyclerViewAdapter<Item : IRecyclerItemViewModel?>(var items: MutableList<Item>) :
    PagingDataAdapter<IRecyclerItemViewModel, IBaseRecyclerViewAdapter.BaseBindingViewHolder>(
        REPO_COMPARATOR
    ) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<IRecyclerItemViewModel>() {
            override fun areItemsTheSame(
                oldItem: IRecyclerItemViewModel, newItem: IRecyclerItemViewModel
            ): Boolean = oldItem.getLayoutId() == newItem.getLayoutId()

            override fun areContentsTheSame(
                oldItem: IRecyclerItemViewModel, newItem: IRecyclerItemViewModel
            ): Boolean = oldItem.getLayoutId() == newItem.getLayoutId()
        }
    }

    private var position = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate(layoutInflater, viewType, parent, false) as ViewDataBinding
        return BaseBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
        this.position = holder.bindingAdapterPosition
        holder.bindData(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        if (items.size < position || items.size == 0) {
            return -1
        }
        return items[position]?.getLayoutId() ?: -1
    }

    fun getItem(holder: BaseBindingViewHolder): Item {
        return items[holder.adapterPosition]
    }

    fun updateData(data: List<Item>) {
        this.items.clear()
        this.items.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: List<Item>) {
        val lastPosition = this.items.size
        this.items.addAll(data)
        notifyItemRangeInserted(lastPosition, data.size)
    }

    fun addItem(item: Item) {
        this.items.add(item)
        notifyItemChanged(itemCount)
    }

    fun addItematInitialPosition(item: Item) {
        this.items.add(0, item)
        notifyItemChanged(itemCount)
    }

    fun addItemAtPosition(position: Int, item: Item) {
        this.items.add(position, item)
        notifyItemInserted(position)
    }

    fun updateItem(item: Item, position: Int, notify: Boolean = true) {
        if (position < this.items.count()) {
            this.items[position] = item
            if (notify) notifyItemChanged(position)
        }
    }

    fun removeItem(position: Int) {
        if (position >= items.size) return
        this.items.removeAt(position)
        notifyDataSetChanged()
    }

    fun removeAllItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    fun getData(): List<Item> {
        return this.items
    }

    fun getItemAt(position: Int): Item? {
        if (position < 0 || position >= itemCount) return null
        return items[position]
    }

    fun addData(data: Item) {
        this.items.add(this.items.size - 1, data)
        notifyItemChanged(this.items.size - 1)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    class BaseBindingViewHolder internal constructor(var binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: Any?) {
            (item as IRecyclerItemViewModel).let {
                for (pair in it.getBindingPairs()) {
                    if (pair.second is ViewHolderPositionModel) {
                        val model =
                            ((pair.second) as ViewHolderPositionModel).copy(position = bindingAdapterPosition)
                        binding.setVariable(pair.first, model)
                    } else {
                        binding.setVariable(pair.first, pair.second)
                    }
                }
            }
            binding.executePendingBindings()
        }

    }


}