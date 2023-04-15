package com.example.upstox.feature.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.upstox.BR
import com.example.upstox.base.IBaseFragment
import com.example.upstox.base.IResult
import com.example.upstox.databinding.FragmentFeedBinding
import com.example.upstox.feature.data.model.FieldData
import com.example.upstox.feature.domain.viewmodel.FeedViewModel
import com.example.upstox.feature.ui.adapter.FeedListItem
import com.example.upstox.feature.ui.adapter.FeedRecyclerAdapter
import com.example.upstox.feature.util.hide
import com.example.upstox.feature.util.roundOffDecimal
import com.example.upstox.feature.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : IBaseFragment<FragmentFeedBinding, FeedViewModel>() {

    override val viewModel: FeedViewModel by viewModels()
    private val _endPoint = "6d0ad460-f600-47a7-b973-4a779ebbaeaf"
    private val adapter by lazy { FeedRecyclerAdapter() }
    private val filedData by lazy { FieldData() }


    override fun getViewBinding(inflater: LayoutInflater): FragmentFeedBinding {
        return FragmentFeedBinding.inflate(inflater)
    }

    override fun setupView() {
        binding.rvFeedItem.adapter = adapter
        viewModel.fetchFeedList(_endPoint)
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
                    binding.bottomView.show()
                    it.data?.data?.map { item ->
                        item.apply {
                            filedData.currentValue =
                                filedData.currentValue + (quantity.times(ltp))
                            filedData.investedValue =
                                filedData.currentValue + (quantity.times(avgPrice.toDouble()))
                            filedData.todayPL = quantity.times(close.minus(ltp))
                            pnl = quantity.times(ltp.minus(avgPrice.toDouble())).roundOffDecimal()
                            adapter.addItem(FeedListItem(this))
                        }
                    }
                    filedData.currentValue.roundOffDecimal()
                    filedData.investedValue.roundOffDecimal()
                    filedData.todayPL = filedData.todayPL.roundOffDecimal()
                    filedData.finalPL =
                        (filedData.currentValue - filedData.investedValue).roundOffDecimal()
                    binding.setVariable(BR.fieldData, filedData)
                    binding.executePendingBindings()
                }
                else -> {}
            }

        }

    }


}