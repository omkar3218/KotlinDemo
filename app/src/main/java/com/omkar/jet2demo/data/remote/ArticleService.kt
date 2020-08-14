package com.omkar.jet2demo.data.remote

import com.omkar.jet2demo.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleService {
    @GET("{page}")
    suspend fun fetchArticles(
        @Path("page") page: Int,
        @Query("q") searchTerm: String
    ): Response<SearchResponse>
}