package com.ricamgar.cleanapp

import android.app.Application
import com.ricamgar.cleanapp.di.ActivitiesModule
import com.ricamgar.cleanapp.di.TestDataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        TestDataModule::class,
        ActivitiesModule::class]
)
interface TestCleanAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun dataModule(dataModule: TestDataModule): Builder

        fun build(): TestCleanAppComponent
    }

    fun inject(application: TestCleanApp)
}