package com.example.upstox.feature.domain.repository

import com.example.upstox.base.IResult
import com.example.upstox.base.performNetworkCall
import com.example.upstox.feature.data.apiInterface.ApiInterface
import com.example.upstox.feature.data.model.FeedMealsListModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@ViewModelScoped
class ItemRepositoryImpl @Inject constructor(private val apiInterface: ApiInterface) :
    ItemRepository {
    override suspend fun fetchFeedModel(queryType: String): Flow<IResult<FeedMealsListModel>> {
        return performNetworkCall {
            apiInterface.fetchMealsList(queryType)
        }
    }

    override suspend fun fetchItemDetail(itemId: String): Flow<IResult<FeedMealsListModel>> {
        return  performNetworkCall {
            apiInterface.fetchItemDetail(itemId)
        }
    }
}