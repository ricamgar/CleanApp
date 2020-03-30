package com.ricamgar.citibox

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TestCitiboxApp : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

//    override fun onCreate() {
//        DaggerTestCitiboxComponent.builder()
//            .application(this)
//            .build()
//            .inject(this)
//        super.onCreate()
//    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}
