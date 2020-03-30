package com.ricamgar.cleanapp.posts.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ricamgar.cleanapp.R
import com.ricamgar.cleanapp.databinding.ActivityPostDetailBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class PostDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PostDetailViewModel by viewModels { viewModelFactory }
    private lateinit var dataBinding: ActivityPostDetailBinding
    private lateinit var commentsAdapter: CommentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)
        dataBinding.lifecycleOwner = this
        dataBinding.viewmodel = viewModel
        viewModel.init(intent.getIntExtra("postId", -1))
        setupCommentsList()
        setupErrorHandling()
    }

    private fun setupCommentsList() {
        dataBinding.viewmodel?.let {
            commentsAdapter = CommentsAdapter(it)
            with(dataBinding.commentsList) {
                val divider = DividerItemDecoration(
                    this@PostDetailActivity, (layoutManager as LinearLayoutManager).orientation
                )
                addItemDecoration(divider)
                adapter = commentsAdapter
            }
        }
    }

    private fun setupErrorHandling() {
        viewModel.postError.observe(this, Observer {
            Toast.makeText(this, getString(R.string.error_loading_post), Toast.LENGTH_SHORT).show()
            finish()
        })
    }

    companion object {
        fun start(context: Context, postId: Int) {
            with(Intent(context, PostDetailActivity::class.java)) {
                putExtra("postId", postId)
                context.startActivity(this)
            }
        }
    }
}
