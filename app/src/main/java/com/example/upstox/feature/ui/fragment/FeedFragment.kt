package com.example.upstox.feature.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.upstox.base.IBaseFragment
import com.example.upstox.base.IResult
import com.example.upstox.databinding.FragmentFeedBinding
import com.example.upstox.feature.domain.viewmodel.FeedViewModel

class FeedFragment : IBaseFragment<FragmentFeedBinding, FeedViewModel>() {

    override val viewModel: FeedViewModel by viewModels()
    private val _endPoint = "6d0ad460-f600-47a7-b973-4a779ebbaeaf"

    override fun getViewBinding(inflater: LayoutInflater): FragmentFeedBinding {
        return FragmentFeedBinding.inflate(inflater)
    }

    override fun setupView() {
        viewModel.fetchFeedList(_endPoint)
    }

    override fun setupObserver() {
        viewModel.feedData.observe(viewLifecycleOwner) {
            when (it) {
                is IResult.Loading -> {}
                is IResult.Error -> {}
                is IResult.Success -> {}
                else -> {}
            }

        }

    }


}