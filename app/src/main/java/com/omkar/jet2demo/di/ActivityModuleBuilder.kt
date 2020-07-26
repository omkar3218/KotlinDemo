package com.omkar.jet2demo.di

import com.omkar.jet2demo.ui.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class ActivityModuleBuilder {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}
