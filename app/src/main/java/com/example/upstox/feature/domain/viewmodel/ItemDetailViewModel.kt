package com.example.upstox.feature.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upstox.base.IResult
import com.example.upstox.feature.data.model.FeedMealsListModel
import com.example.upstox.feature.domain.usecase.GetItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(private val feedDataUseCase: GetItemUseCase) :
    ViewModel() {

    private var _itemDetail = MutableLiveData<IResult<FeedMealsListModel>>()
    val itemDetail: LiveData<IResult<FeedMealsListModel>> = _itemDetail


    fun fetchFeedList(endPoint: String) {
        viewModelScope.launch {
            feedDataUseCase(endPoint).catch {
                _itemDetail.postValue(IResult.Error(it))
            }.collect {
                _itemDetail.postValue(it)
            }
        }
    }

}