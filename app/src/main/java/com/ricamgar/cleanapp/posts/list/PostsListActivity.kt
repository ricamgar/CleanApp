package com.ricamgar.cleanapp.posts.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ricamgar.cleanapp.R
import com.ricamgar.cleanapp.databinding.ActivityPostsListBinding
import com.ricamgar.cleanapp.posts.detail.PostDetailActivity
import com.ricamgar.cleanapp.util.EventObserver
import dagger.android.AndroidInjection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostsListActivity : AppCompatActivity() {

    private val viewModel: PostsListViewModel by viewModels()
    private lateinit var postsAdapter: PostsAdapter
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
            postsAdapter = PostsAdapter(it)
            with(dataBinding.postsList) {
                val divider = DividerItemDecoration(
                    this@PostsListActivity, (layoutManager as LinearLayoutManager).orientation
                )
                addItemDecoration(divider)
                adapter = postsAdapter
            }
        }
    }

    private fun setupNavigationListeners() {
        viewModel.openPostEvent.observe(this, EventObserver {
            PostDetailActivity.start(this, it)
        })
    }
}
