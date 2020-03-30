package com.ricamgar.cleanapp.posts.detail

import androidx.lifecycle.ViewModel
import com.ricamgar.cleanapp.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PostDetailModule {

    @ContributesAndroidInjector
    abstract fun contributePostsListActivity(): PostDetailActivity

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailViewModel::class)
    internal abstract fun postDetailViewModel(viewModel: PostDetailViewModel): ViewModel
}
