package com.ricamgar.citibox.posts.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ricamgar.citibox.R
import com.ricamgar.citibox.databinding.ActivityPostsListBinding
import com.ricamgar.citibox.di.util.Injectable
import com.ricamgar.citibox.posts.detail.PostDetailActivity
import com.ricamgar.citibox.util.EventObserver
import javax.inject.Inject

class PostsListActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PostsListViewModel by viewModels { viewModelFactory }
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
