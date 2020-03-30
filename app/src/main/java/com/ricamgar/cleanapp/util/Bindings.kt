package com.ricamgar.cleanapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ricamgar.data.remote.ImageLoader


@BindingAdapter("avatarId", "imageLoader", requireAll = true)
fun loadAvatar(imageView: ImageView, id: Int, loader: ImageLoader) {
    val url = "https://api.adorable.io/avatars/100/$id.png"
    loader.load(url, imageView)
}
