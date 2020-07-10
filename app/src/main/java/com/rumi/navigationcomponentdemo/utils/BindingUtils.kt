package com.rumi.navigationcomponentdemo.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.rumi.navigationcomponentdemo.R

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, path: String?) {
    if (path.isNullOrEmpty()) {
        imageView.setImageResource(android.R.drawable.stat_notify_error)
        return
    }
    Glide.with(imageView.context)
        .load(path.trim())
        .into(imageView)
}