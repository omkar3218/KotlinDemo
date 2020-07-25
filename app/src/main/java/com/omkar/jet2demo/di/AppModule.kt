package com.omkar.jet2demo.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.omkar.jet2demo.Jet2Application
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
class AppModule {

    @Provides
    fun provideContext(app: Jet2Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }


}
