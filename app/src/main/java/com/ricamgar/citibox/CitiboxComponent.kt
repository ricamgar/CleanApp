package com.ricamgar.citibox

import android.app.Application
import com.ricamgar.citibox.di.ActivitiesModule
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
interface CitiboxComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CitiboxComponent
    }

    fun inject(application: CitiboxApp)
}