package com.example.upstox.feature.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.upstox.base.IResult
import com.example.upstox.base.performNetworkCall
import com.example.upstox.feature.data.apiInterface.ApiInterface
import com.example.upstox.feature.data.model.FeedItem
import com.example.upstox.feature.domain.repository.NETWORK_PAGE_SIZE
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@ViewModelScoped
class PagingDataSource @Inject constructor(private val apiInterface: ApiInterface) :
    PagingSource<Int, FeedItem>() {

    override fun getRefreshKey(state: PagingState<Int, FeedItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeedItem> {
        val page = params.key ?: 1
        try {
            var loadResult: LoadResult<Int, FeedItem>? = null
            performNetworkCall { apiInterface.fetchResponseModel(page) }.catch {
                loadResult = LoadResult.Error(it)
            }.collect {
                loadResult = when (it) {
                    is IResult.Success -> {
                        if (it.data?.response?.productsList.isNullOrEmpty()) {
                            LoadResult.Page(
                                listOf(FeedItem.NoMorePage(id = 111)),
                                prevKey = if (page == 1) null else page - 1,
                                nextKey = if (it.data?.response?.productsList?.isEmpty() == true) null else page + 1
                            )
                        } else {
                            it.data?.response?.productsList?.map {
                                FeedItem.FeedItemModel(it)
                            }?.let { feeItemList ->
                                LoadResult.Page(
                                    feeItemList,
                                    prevKey = if (page == 1) null else page - 1,
                                    nextKey = if (it.data.response.productsList?.isEmpty() == true) null else {
                                        page + (params.loadSize / (NETWORK_PAGE_SIZE))

                                    }
                                )
                            }

                        }
                    }

                    else -> {
                        LoadResult.Error((it as IResult.Error).throwable)
//                        LoadResult.Page(
//                            listOf(FeedItem.NoMorePage(id = 111)),
//                            prevKey = if (page == 1) null else page - 1,
//                            nextKey = null
//                        )
                    }
                }
            }
            return loadResult!!

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


}