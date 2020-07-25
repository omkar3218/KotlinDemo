package com.omkar.jet2demo.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.omkar.jet2demo.data.local.AppDatabase
import com.omkar.jet2demo.data.remote.RemoteRepository
import com.omkar.jet2demo.data.remote.RemoteRepository_Factory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ArticleListViewModel @Inject constructor(override var coroutineContext: CoroutineContext) : ViewModel(), CoroutineScope {
    @Inject
    lateinit var remoteRepository: RemoteRepository

    fun fetchArticleList(){
        launch{
          val response =  remoteRepository.requestArticles(1,10)
            AppDatabase.instance?.saveArticleDataModel(response.data)
        }
    }
}
