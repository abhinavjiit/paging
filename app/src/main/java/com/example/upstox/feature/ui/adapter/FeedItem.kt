package com.example.upstox.feature.ui.adapter

import com.example.upstox.base.IRecyclerItemViewModel

sealed class FeedItem(private val layoutId: Int) : IRecyclerItemViewModel {

    override fun getLayoutId(): Int = layoutId


}


