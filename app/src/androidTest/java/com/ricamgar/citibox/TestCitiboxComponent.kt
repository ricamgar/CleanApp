package com.ricamgar.citibox

import android.app.Application
import com.ricamgar.citibox.di.ActivitiesModule
import com.ricamgar.citibox.di.TestDataModule
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
interface TestCitiboxComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun dataModule(dataModule: TestDataModule): Builder

        fun build(): TestCitiboxComponent
    }

    fun inject(application: TestCitiboxApp)
}