package com.example.upstox.feature.ui.fragment.itemlist

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.upstox.R
import com.example.upstox.base.IBaseFragment
import com.example.upstox.base.IResult
import com.example.upstox.databinding.FragmentFeedBinding
import com.example.upstox.feature.domain.viewmodel.FeedViewModel
import com.example.upstox.feature.ui.adapter.FeedListItem
import com.example.upstox.feature.ui.adapter.FeedRecyclerAdapter
import com.example.upstox.feature.ui.fragment.itemdetail.ItemDetailFragment
import com.example.upstox.feature.util.hide
import com.example.upstox.feature.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : IBaseFragment<FragmentFeedBinding, FeedViewModel>() {

    override val viewModel: FeedViewModel by viewModels()
    private val _queryType = "Dessert"
    private val adapter by lazy { FeedRecyclerAdapter() }


    override fun getViewBinding(inflater: LayoutInflater): FragmentFeedBinding {
        return FragmentFeedBinding.inflate(inflater)
    }

    override fun setupView() {
        binding.rvFeedItem.adapter = adapter
        viewModel.fetchFeedList(_queryType)
    }

    override fun setupObserver() {
        viewModel.feedData.observe(viewLifecycleOwner) {
            when (it) {
                is IResult.Loading -> {
                    binding.loader.show()
                }
                is IResult.Error -> {

                }
                is IResult.Success -> {
                    binding.loader.hide()
                    it.data?.meals?.forEach { meal ->
                        adapter.addItem(FeedListItem(meal, callback))
                    }
                }
                else -> {}
            }
        }
    }


    private val callback = object : ItemClick {
        override fun onItemClick(itemId: String) {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragmentConatineView,
                    ItemDetailFragment
                        .newInstance(itemId)
                ).addToBackStack(null)
                .commit()
        }
    }


    interface ItemClick {
        fun onItemClick(itemId: String)
    }

}