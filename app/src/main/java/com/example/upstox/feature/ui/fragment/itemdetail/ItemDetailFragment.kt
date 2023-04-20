package com.example.upstox.feature.ui.fragment.itemdetail

import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.upstox.BR
import com.example.upstox.base.IBaseFragment
import com.example.upstox.base.IResult
import com.example.upstox.databinding.FragmentItemListBinding
import com.example.upstox.feature.domain.viewmodel.ItemDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

const val ITEM_ID = "itemId"

@AndroidEntryPoint
class ItemDetailFragment : IBaseFragment<FragmentItemListBinding, ItemDetailViewModel>() {
    override val viewModel: ItemDetailViewModel by viewModels()

    private val itemId: String? by lazy { requireArguments().getString(ITEM_ID) }

    override fun getViewBinding(inflater: LayoutInflater): FragmentItemListBinding {
        return FragmentItemListBinding.inflate(inflater)
    }

    override fun setupView() {
        itemId?.let {
            viewModel.fetchFeedList(it)
        }

    }

    override fun setupObserver() {
        viewModel.itemDetail.observe(viewLifecycleOwner) {

            when (it) {
                is IResult.Success -> {
                    it.data?.meals?.get(0)?.let {
                        binding.setVariable(BR.item, it)
                    }
                }
                is IResult.Loading -> {}
                else -> {}

            }

        }

    }


    companion object {

        fun newInstance(itemId: String): ItemDetailFragment {
            return ItemDetailFragment().also {
                it.arguments = bundleOf(ITEM_ID to itemId)
            }
        }
    }


}