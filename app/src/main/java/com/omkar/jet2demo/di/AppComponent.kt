package com.omkar.jet2demo.di

import com.omkar.jet2demo.Jet2Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
@Component(modules = [AndroidInjectionModule::class])

interface AppComponent: AndroidInjector<Jet2Application> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Jet2Application): Builder
        fun build(): AppComponent
    }
}