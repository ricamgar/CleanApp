package com.ricamgar.cleanapp.posts.list

import androidx.lifecycle.ViewModel
import com.ricamgar.cleanapp.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PostsListModule {

    @ContributesAndroidInjector
    abstract fun contributePostsListActivity(): PostsListActivity

    @Binds
    @IntoMap
    @ViewModelKey(PostsListViewModel::class)
    internal abstract fun postListViewModel(viewModel: PostsListViewModel): ViewModel
}
