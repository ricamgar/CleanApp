package com.ricamgar.citibox.posts.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.ricamgar.citibox.R
import com.ricamgar.citibox.databinding.ActivityPostsListBinding
import com.ricamgar.citibox.di.util.Injectable
import com.ricamgar.citibox.posts.detail.PostDetailActivity
import javax.inject.Inject

class PostsListActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PostsListViewModel by viewModels { viewModelFactory }
    private lateinit var adapter: PostsAdapter
    private lateinit var dataBinding: ActivityPostsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_posts_list)
        dataBinding.lifecycleOwner = this
        dataBinding.viewmodel = viewModel
        setupPostsList()
        setupNavigationListeners()
    }

    private fun setupPostsList() {
        dataBinding.viewmodel?.let {
            adapter = PostsAdapter(it)
            dataBinding.postsList.adapter = adapter
        }
    }

    private fun setupNavigationListeners() {
        viewModel.openPostEvent.observe(this) {
            with(Intent(this, PostDetailActivity::class.java)) {
                putExtra("postId", it)
                startActivity(this)
            }
        }
    }
}
