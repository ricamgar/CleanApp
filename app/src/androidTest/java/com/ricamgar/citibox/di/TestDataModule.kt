package com.ricamgar.citibox.di

import android.widget.ImageView
import com.ricamgar.data.remote.ImageLoader
import com.ricamgar.domain.repository.PostsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestDataModule(
    private val postsRepository: PostsRepository
) {
    @Provides
    @Singleton
    fun providePostsRepository() = postsRepository

    @Provides
    @Singleton
    fun provideImageLoader() = object : ImageLoader {
        override fun load(url: String, view: ImageView) {

        }
    }
}