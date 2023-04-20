package com.example.upstox.feature.ui.adapter

import com.example.upstox.BR
import com.example.upstox.R
import com.example.upstox.base.IRecyclerItemViewModel
import com.example.upstox.feature.data.model.Meal
import com.example.upstox.feature.ui.fragment.itemlist.FeedFragment

sealed class FeedItem(private val layoutId: Int) : IRecyclerItemViewModel {

    override fun getLayoutId(): Int = layoutId

}


class FeedListItem(private val item: Meal, private val callback: FeedFragment.ItemClick) :
    FeedItem(R.layout.adapter_item) {
    override fun getBindingPairs(): List<Pair<Int, Any>> {
        return listOf(BR.item to item, BR.callback to callback)
    }
}


