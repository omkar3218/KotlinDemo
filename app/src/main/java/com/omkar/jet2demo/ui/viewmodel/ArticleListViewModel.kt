package com.omkar.jet2demo.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.omkar.jet2demo.data.local.AppDatabase
import com.omkar.jet2demo.data.model.Article
import com.omkar.jet2demo.data.remote.RemoteRepository
import com.omkar.jet2demo.data.remote.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ArticleListViewModel @Inject constructor(override var coroutineContext: CoroutineContext) :
    ViewModel(), CoroutineScope {
    @Inject
    lateinit var remoteRepository: RemoteRepository

    var isLoading = MutableLiveData<Boolean>()


    var articleLiveData = MutableLiveData<List<Article>>()

    fun fetchArticleList(pageNumber: Int) {
        launch {
            isLoading.postValue(true)
            val response = remoteRepository.requestArticles(pageNumber, 10)
            when (response) {
                is Resource.Success -> {
                    isLoading.postValue(false)
                    AppDatabase.instance?.saveArticleDataModel(response.data)
                    articleLiveData.postValue(response.data)
                }
                is Resource.DataError -> {
                    val list = AppDatabase.instance?.getArticles(pageNumber)
                    articleLiveData.postValue(list)
                    isLoading.postValue(false)

                }
            }

        }
    }
}
