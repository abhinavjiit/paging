package com.example.upstox.feature.domain.usecase

import com.example.upstox.base.IBaseUseCase
import com.example.upstox.base.IResult
import com.example.upstox.feature.data.model.FeedMealsListModel
import com.example.upstox.feature.domain.repository.ItemRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetItemUseCase @Inject constructor(private val repository: ItemRepository) :
    IBaseUseCase<String, FeedMealsListModel> {
    override suspend fun invoke(input: String): Flow<IResult<FeedMealsListModel>> {
        return repository.fetchItemDetail(input)
    }
}