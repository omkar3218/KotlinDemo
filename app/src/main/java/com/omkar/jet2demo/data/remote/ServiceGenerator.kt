package com.omkar.jet2demo.data.remote

import com.google.gson.Gson
import com.omkar.jet2demo.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ServiceGenerator @Inject
constructor(private val gson: Gson) {

    //Network constants
    private val TIMEOUT_CONNECT = 30   //In seconds
    private val TIMEOUT_READ = 30   //In seconds

    private val CONTENT_TYPE = "Authorization"   //In seconds
    private val CONTENT_TYPE_VALUE = "Client-ID 137cda6b5008a7c"   //In seconds


    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private var retrofit: Retrofit? = null


    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply {
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
                }.level = HttpLoggingInterceptor.Level.BODY
            }
            return loggingInterceptor
        }

    private val headerInterceptor = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    init {
       //okHttpBuilder.addInterceptor{ it.proceed(it.request().newBuilder().addHeader("Cache-Control", "no-store").build())}
        okHttpBuilder.addInterceptor(headerInterceptor)
        okHttpBuilder.addInterceptor(logger)
        okHttpBuilder.connectTimeout(TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
    }

    fun <S> createService(serviceClass: Class<S>, baseUrl: String): S {
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit!!.create(serviceClass)
    }

}
