package com.omkar.jet2demo.di

import com.omkar.jet2demo.Jet2Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModuleBuilder::class,
        FragmentModuleBuilder::class,
        ViewModelModule::class]
)

interface AppComponent : AndroidInjector<Jet2Application> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Jet2Application): Builder
        fun build(): AppComponent
    }
}