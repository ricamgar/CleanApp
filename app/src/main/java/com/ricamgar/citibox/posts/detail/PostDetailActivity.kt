package com.ricamgar.citibox.posts.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ricamgar.citibox.R
import com.ricamgar.citibox.databinding.ActivityPostDetailBinding
import com.ricamgar.citibox.di.util.Injectable
import javax.inject.Inject

class PostDetailActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: PostDetailViewModel by viewModels { viewModelFactory }
    private lateinit var dataBinding: ActivityPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)
        dataBinding.lifecycleOwner = this
        dataBinding.viewmodel = viewModel
    }
}
