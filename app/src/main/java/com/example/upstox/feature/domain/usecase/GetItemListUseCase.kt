package com.example.upstox.feature.domain.usecase

import androidx.paging.PagingData
import com.example.upstox.base.IBaseUseCase
import com.example.upstox.feature.data.model.FeedItem
import com.example.upstox.feature.domain.repository.ItemRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetItemListUseCase @Inject constructor(private val repository: ItemRepository) :
    IBaseUseCase<PagingData<FeedItem>> {
    override suspend fun invoke(): Flow<PagingData<FeedItem>> {
        return repository.fetchResponseModel()
    }

}