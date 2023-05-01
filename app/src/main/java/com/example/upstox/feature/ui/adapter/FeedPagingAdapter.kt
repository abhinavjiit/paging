package com.example.upstox.feature.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.upstox.BR
import com.example.upstox.R
import com.example.upstox.feature.data.model.FeedItem

const val LOADING_VIEW = 1

class FeedPagingAdapter : PagingDataAdapter<FeedItem, ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<FeedItem>() {
            override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
                oldItem.itemId == newItem.itemId

            override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.adapter_item -> {
                val binding = DataBindingUtil.inflate(
                    layoutInflater, viewType, parent, false
                ) as ViewDataBinding
                FeedItemViewHolder(binding)
            }

            else -> {
                val binding = DataBindingUtil.inflate(
                    layoutInflater, viewType, parent, false
                ) as ViewDataBinding
                FeedNoMoreDataViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is FeedItemViewHolder -> {
                holder.bind(getItem(position) as FeedItem.FeedItemModel)
            }

            else -> {
                (holder as FeedNoMoreDataViewHolder).bind(getItem(position))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        //for showing place holders, remove load adapter , add itemsAfter in LoadResult
//        if (getItem(position) == null) {
//            return R.layout.item_no_more_data
//        }
//

        if (position == itemCount ) {
            return LOADING_VIEW
        }

        return when (getItem(position)) {
            is FeedItem.FeedItemModel -> R.layout.adapter_item
            else -> R.layout.item_no_more_data
        }
    }

    inner class FeedItemViewHolder(private val binding: ViewDataBinding) :
        ViewHolder(binding.root) {

        fun bind(item: FeedItem.FeedItemModel?) {
            item?.let {
                binding.setVariable(BR.item, item.itemModel)
            }
        }
    }

    inner class FeedNoMoreDataViewHolder(private val binding: ViewDataBinding) :
        ViewHolder(binding.root) {

        fun bind(item: FeedItem?) {
            item?.let {
                binding.setVariable(BR.item, item)
            }
        }
    }

}