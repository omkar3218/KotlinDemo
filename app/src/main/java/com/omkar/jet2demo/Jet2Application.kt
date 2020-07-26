package com.omkar.jet2demo

import com.omkar.jet2demo.data.local.AppDatabase
import com.omkar.jet2demo.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.realm.Realm
import javax.inject.Inject

open class Jet2Application : DaggerApplication(), HasAndroidInjector {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>


    override fun androidInjector() = dispatchingAndroidInjector

    override fun onTerminate() {
        super.onTerminate()
        AppDatabase.instance?.closeDB()
    }

}