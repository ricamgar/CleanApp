package com.ricamgar.citibox.di

import com.ricamgar.citibox.posts.detail.PostDetailModule
import com.ricamgar.citibox.posts.list.PostsListModule
import dagger.Module

@Module(
    includes = [
        ViewModelModule::class,
        PostsListModule::class,
        PostDetailModule::class]
)
abstract class ActivitiesModule