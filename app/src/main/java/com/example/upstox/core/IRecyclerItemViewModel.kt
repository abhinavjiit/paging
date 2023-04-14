package com.example.upstox.core

import androidx.annotation.LayoutRes


interface IRecyclerItemViewModel {

    @LayoutRes
    fun getLayoutId(): Int

    fun getBindingPairs(): List<Pair<Int, Any>>

}