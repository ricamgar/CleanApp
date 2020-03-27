package com.ricamgar.citibox.posts.list

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.ricamgar.citibox.R
import com.ricamgar.citibox.di.util.Injectable
import javax.inject.Inject

class PostsListActivity : AppCompatActivity(),
    Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PostsListViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_list)

        viewModel.getPosts().observe(this) {
            Log.e("POSTS", it.toString())
        }
    }
}
