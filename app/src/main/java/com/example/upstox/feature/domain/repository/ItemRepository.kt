package com.example.upstox.feature.domain.repository

import com.example.upstox.base.IResult
import com.example.upstox.feature.data.model.FeedMealsListModel
import kotlinx.coroutines.flow.Flow


interface ItemRepository {
    suspend fun fetchFeedModel(queryType: String): Flow<IResult<FeedMealsListModel>>
    suspend fun fetchItemDetail(itemId: String): Flow<IResult<FeedMealsListModel>>
}