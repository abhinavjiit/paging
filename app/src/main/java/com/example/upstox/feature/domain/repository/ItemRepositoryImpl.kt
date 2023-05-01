package com.example.upstox.feature.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.upstox.feature.data.model.FeedItem
import com.example.upstox.feature.domain.paging.PagingDataSource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 20

@ViewModelScoped
class ItemRepositoryImpl @Inject constructor(private val pagingDataSource: PagingDataSource) :
    ItemRepository {

    override fun fetchResponseModel(): Flow<PagingData<FeedItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = NETWORK_PAGE_SIZE,               // because default is 3*loadSize
                prefetchDistance = NETWORK_PAGE_SIZE.div(2) // default value is equal to loadSize
            ),
            pagingSourceFactory = { pagingDataSource }

        ).flow
    }

}