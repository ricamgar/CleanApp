package com.ricamgar.citibox.di

import com.ricamgar.citibox.posts.list.PostsListModule
import dagger.Module

@Module(
    includes = [
        ViewModelModule::class,
        PostsListModule::class]
)
abstract class ActivitiesModule