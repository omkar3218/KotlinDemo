package com.omkar.jet2demo.di

import com.omkar.jet2demo.ui.view.ArticleListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModuleBuilder {

    @ContributesAndroidInjector
    abstract fun contributeArticleListFragment(): ArticleListFragment


}
