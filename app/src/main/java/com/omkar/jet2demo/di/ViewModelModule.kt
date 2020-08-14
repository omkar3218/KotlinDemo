package com.omkar.jet2demo.di

import androidx.lifecycle.ViewModel
import com.omkar.jet2demo.ui.viewmodel.ArticleListViewModel
import com.omkar.jet2demo.ui.viewmodel.ImageDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress("unused")
@Module
abstract class ViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(ArticleListViewModel::class)
    abstract fun bindArticleListViewModel(viewModel: ArticleListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ImageDetailsViewModel::class)
    abstract fun bindImageDetailsViewModel(imageViewModel: ImageDetailsViewModel): ViewModel

}
