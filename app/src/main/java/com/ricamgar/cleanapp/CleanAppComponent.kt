package com.ricamgar.cleanapp

import android.app.Application
import com.ricamgar.cleanapp.di.ActivitiesModule
import com.ricamgar.data.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        DataModule::class,
        ActivitiesModule::class]
)
interface CleanAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CleanAppComponent
    }

    fun inject(application: CleanApp)
}