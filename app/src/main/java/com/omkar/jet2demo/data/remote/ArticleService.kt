package com.omkar.jet2demo.data.remote

import com.omkar.jet2demo.data.model.Article
import io.realm.RealmList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleService {
    @GET("blogs")
    suspend fun fetchArticles(@Query("page") page: Int, @Query("limit") limit:Int): Response<RealmList<Article>>
}