package com.example.upstox.feature.ui.fragment.itemlist

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.upstox.R
import com.example.upstox.base.IBaseFragment
import com.example.upstox.databinding.FragmentFeedBinding
import com.example.upstox.feature.domain.viewmodel.FeedViewModel
import com.example.upstox.feature.ui.adapter.FeedPagingAdapter
import com.example.upstox.feature.ui.adapter.LoaderAdapter
import com.example.upstox.feature.util.hide
import com.example.upstox.feature.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedFragment : IBaseFragment<FragmentFeedBinding, FeedViewModel>() {

    override val viewModel: FeedViewModel by viewModels()
    private val adapter by lazy { FeedPagingAdapter() }
    private val loadAdapter by lazy { LoaderAdapter() }


    override fun getViewBinding(inflater: LayoutInflater): FragmentFeedBinding {
        return FragmentFeedBinding.inflate(inflater)
    }

    override fun setupView() {
        binding.rvFeedItem.adapter = adapter.withLoadStateFooter(loadAdapter)
        viewModel.fetchFeedItems()
        setupListener()
    }

    override fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.feedItems.collect {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun setupListener() {
        setupLoadStateListener()
        setupItemSpanSize()
    }

    private fun setupLoadStateListener() {
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading
                && !it.append.endOfPaginationReached
                && adapter.itemCount < 1
            ) {
                binding.loader.show()
            } else if (it.refresh is LoadState.NotLoading
                && adapter.itemCount > 1
            ) {
                binding.loader.hide()
            } else if (it.refresh is LoadState.Error) {
                // show toast some thing went wrong
            }
        }
    }

    private fun setupItemSpanSize() {
        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (adapter.getItemViewType(position)) {
                    R.layout.adapter_item -> 1
                    else -> 2
                }
            }

        }
        (binding.rvFeedItem.layoutManager as GridLayoutManager).spanSizeLookup = spanSizeLookup
    }

}