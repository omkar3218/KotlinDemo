package com.omkar.jet2demo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omkar.jet2demo.data.model.Article
import com.omkar.jet2demo.data.remote.RemoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ArticleListViewModel @Inject constructor(override var coroutineContext: CoroutineContext) :
    ViewModel(), CoroutineScope {
    @Inject
    lateinit var remoteRepository: RemoteRepository

    var articleLiveData = MutableLiveData<List<Article>>()

    fun fetchArticleList(pageNumber: Int) {
        launch {
            val response = remoteRepository.requestArticles(pageNumber, 10)
            //  AppDatabase.instance?.saveArticleDataModel(response.data)
            articleLiveData.postValue(response.data)
        }
    }
}
