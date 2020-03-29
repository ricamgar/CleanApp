package com.ricamgar.citibox.posts.detail

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ricamgar.domain.model.Comment

@BindingAdapter("items")
fun setItems(listView: RecyclerView, items: List<Comment>) {
    (listView.adapter as CommentsAdapter).submitList(items)
}

@BindingAdapter("mailImage")
fun setMailImage(textView: TextView, email: String) {
    when (email.substringAfter(".")) {
        "info" -> {
            textView.text = "ℹ️"
            textView.visibility = VISIBLE
        }
        "co.uk" -> {
            textView.text = "🇬🇧"
            textView.visibility = VISIBLE
        }
        else -> textView.visibility = GONE
    }
}