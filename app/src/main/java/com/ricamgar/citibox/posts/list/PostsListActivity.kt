package com.ricamgar.citibox.posts.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ricamgar.citibox.R
import com.ricamgar.citibox.databinding.ActivityPostsListBinding
import com.ricamgar.citibox.di.util.Injectable
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
        adapter = PostsAdapter(viewModel)
        dataBinding.postsList.adapter = adapter
    }
}
