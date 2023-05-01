package com.example.upstox.feature.domain.repository

import androidx.paging.PagingData
import com.example.upstox.feature.data.model.FeedItem
import kotlinx.coroutines.flow.Flow


interface ItemRepository {
    fun fetchResponseModel(): Flow<PagingData<FeedItem>>
}