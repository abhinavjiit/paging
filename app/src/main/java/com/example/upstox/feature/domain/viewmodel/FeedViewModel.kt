package com.example.upstox.feature.domain.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.upstox.feature.data.model.FeedItem
import com.example.upstox.feature.domain.usecase.GetItemListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: GetItemListUseCase
) : ViewModel() {

    private var _saveSateList: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private var _feedItems: MutableStateFlow<PagingData<FeedItem>> =
        MutableStateFlow(PagingData.empty())
    val feedItems: StateFlow<PagingData<FeedItem>> = _feedItems

    fun fetchFeedItems() {
        viewModelScope.launch {
            if (!_saveSateList.value) {
                useCase().cachedIn(this).collect {
                    _saveSateList.value = true
                    _feedItems.value = it
                }
            }
        }
    }

}