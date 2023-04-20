package com.example.upstox.feature.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.upstox.R


@BindingAdapter("app:setImageUrl")
fun ImageView.setImageUrl(imageUrl: String?) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(100, 100)

    val requestBuilder: RequestBuilder<Drawable> = Glide.with(this.context)
        .asDrawable().sizeMultiplier(0.25f)

    Glide.with(this.context)
        .load(imageUrl)
        .thumbnail(requestBuilder)
        .apply(requestOptions)
        .error(R.drawable.ic_launcher_background)
        .into(this)

}