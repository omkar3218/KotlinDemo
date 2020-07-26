package com.omkar.jet2demo.data.local

import com.omkar.jet2demo.BuildConfig
import com.omkar.jet2demo.data.model.Article
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.RealmResults

class AppDatabase private constructor() {


    private fun prepareRealmInstance() {
        val config = RealmConfiguration.Builder().name("article.realm")
            .schemaVersion(BuildConfig.SCHEMA_VERSION.toLong())
            .build()
        Realm.setDefaultConfiguration(config)
        Realm.compactRealm(config)
    }

    fun closeDB() {
        if (Realm.getDefaultInstance() != null) Realm.getDefaultInstance()!!.close()
    }

    fun saveArticleDataModel(articleList: RealmList<Article>?) {
        Realm.getDefaultInstance()!!.beginTransaction()
        Realm.getDefaultInstance()!!.copyToRealmOrUpdate(articleList)
        Realm.getDefaultInstance()!!.commitTransaction()
    }

    fun getArticles(pageNumber: Int): List<Article> {
        val list: MutableList<Article> = ArrayList()
        val articleList: RealmResults<Article> =
            Realm.getDefaultInstance()!!.where(Article::class.java)
                .between("id", (pageNumber - 1) * 10, pageNumber * 10).findAll()

        list.addAll(Realm.getDefaultInstance()!!.copyFromRealm(articleList))

        return list
    }

    companion object {
        private var appDatabase: AppDatabase? = null

        val instance: AppDatabase?
            get() {
                if (appDatabase == null)
                    appDatabase = AppDatabase()
                return appDatabase
            }
    }

    init {
        prepareRealmInstance()
    }
}