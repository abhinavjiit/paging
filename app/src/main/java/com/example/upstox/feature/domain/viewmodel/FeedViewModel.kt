package com.example.upstox.feature.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upstox.base.IResult
import com.example.upstox.feature.data.model.FeedModel
import com.example.upstox.feature.domain.repository.FeedDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val repository: FeedDataRepository) : ViewModel() {

    private var _feedData = MutableLiveData<IResult<FeedModel>>()
    val feedData: LiveData<IResult<FeedModel>> = _feedData

    fun fetchFeedList(endPoint: String) {
        viewModelScope.launch {
            repository.fetchFeedModel(endPoint).catch {
                _feedData.postValue(IResult.Error(it))
            }.collect {
                _feedData.postValue(it)
            }
        }
    }


}