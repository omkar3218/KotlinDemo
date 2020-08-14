package com.omkar.jet2demo.data.remote

import android.content.Context
import com.omkar.jet2demo.BuildConfig
import com.omkar.jet2demo.data.model.Data
import com.omkar.jet2demo.data.model.SearchResponse
import com.omkar.jet2demo.data.remote.Network.Utils.isConnected
import io.realm.RealmList
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


class RemoteRepository @Inject
constructor(private val serviceGenerator: ServiceGenerator, private val context: Context) {

    suspend fun requestArticles(page: Int, searchTerm: String): Resource<SearchResponse> {
        val articleService =
            serviceGenerator.createService(ArticleService::class.java, BuildConfig.BASE_URL)
        val response = processCall { articleService.fetchArticles(page, searchTerm) }
        return if (response is SearchResponse) {
            Resource.Success(data = response)
        } else {
            Resource.DataError(error = response as AppError)
        }

    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!isConnected(context)) {
            return AppError(AppError.NO_INTERNET_CONNECTION, "No internet")
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                return AppError(responseCode, "Request failed")
            }
        } catch (e: IOException) {
            return AppError(AppError.NETWORK_ERROR, "Network Error")
        }
    }

}
