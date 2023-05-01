package com.example.upstox.feature.util

import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import com.bumptech.glide.Glide
import com.example.upstox.R


@BindingAdapter("app:setImageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    Glide.with(this.context)
        .load(imageUrl)
        .error(R.drawable.ic_launcher_background)
        .into(this)

}


@BindingAdapter("app:setFooter", "app:progressBar")
fun TextView.setFooter(loadState: LoadState, progressBar: ProgressBar) {
    when (loadState) {
        is LoadState.Loading -> {
            progressBar.show()
            this.hide()
        }
        is LoadState.Error -> {
            progressBar.hide()
            this.show()
        }
        else -> {
            progressBar.hide()
            this.hide()
        }


    }


}
