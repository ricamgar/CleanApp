package com.ricamgar.data.remote

import android.widget.ImageView

interface ImageLoader {
    fun load(url: String, view: ImageView)
}