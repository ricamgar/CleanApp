package com.ricamgar.citibox.posts.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ricamgar.domain.model.Post

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Post>) {
    (listView.adapter as PostsAdapter).submitList(items)
}