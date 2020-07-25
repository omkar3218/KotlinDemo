package com.omkar.jet2demo.data.remote

import android.content.Context
import com.omkar.jet2demo.BuildConfig
import com.omkar.jet2demo.data.model.Article
import com.omkar.jet2demo.data.remote.Network.Utils.isConnected
import io.realm.RealmList
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


class RemoteRepository @Inject
constructor(private val serviceGenerator: ServiceGenerator, private val context: Context) {

    suspend fun requestArticles(page: Int, limit: Int): Resource<RealmList<Article>> {
        val articleService =
            serviceGenerator.createService(ArticleService::class.java, BuildConfig.BASE_URL)
        val response = processCall { articleService.fetchArticles(page, limit) }
        return if (response is RealmList<*>) {
            Resource.Success(data = response as RealmList<Article>)
        } else {
            Resource.DataError(error = response as AppError)
        }

    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!isConnected(context)) {
            return AppError(-1, "No internet")
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
            return AppError(-1, "Network Error")
        }
    }

}
