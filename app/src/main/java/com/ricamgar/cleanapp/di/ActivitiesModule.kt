package com.ricamgar.cleanapp.di

import com.ricamgar.cleanapp.posts.detail.PostDetailModule
import com.ricamgar.cleanapp.posts.list.PostsListModule
import dagger.Module

@Module(
    includes = [
        ViewModelModule::class,
        PostsListModule::class,
        PostDetailModule::class]
)
abstract class ActivitiesModule