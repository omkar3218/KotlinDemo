package com.omkar.jet2demo.data.local

import com.omkar.jet2demo.BuildConfig
import com.omkar.jet2demo.data.model.Article
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.RealmResults

class AppDatabase private constructor() {
    private var realmInstance: Realm? = null

    /**
     * Gets realm instance.
     *
     * @return the realm instance
     */
    private fun getRealmInstance(): Realm? {
        openDB()
        return realmInstance
    }

    private fun openDB() {
        if (realmInstance == null || realmInstance!!.isClosed)
            realmInstance = Realm.getDefaultInstance()
    }

    private fun prepareRealmInstance() {
        val config = RealmConfiguration.Builder().name("article.realm")
            .schemaVersion(BuildConfig.SCHEMA_VERSION.toLong())
            .build()
        Realm.setDefaultConfiguration(config)
        Realm.compactRealm(config)
        openDB()
    }

    fun closeDB() {
        if (realmInstance != null) realmInstance!!.close()
    }

    private fun beginTransaction() {
        getRealmInstance()!!.beginTransaction()
    }

    private fun commitTransaction() {
        getRealmInstance()!!.commitTransaction()
        Realm.compactRealm(Realm.getDefaultConfiguration())
    }

    fun saveArticleDataModel(articleList: RealmList<Article>?) {
        try {
            getRealmInstance()!!.beginTransaction()
            getRealmInstance()!!.copyToRealmOrUpdate(articleList)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            commitTransaction()
        }
    }

    fun getArticles(): RealmResults<Article?> {
        val articleList: RealmResults<Article?> =
            getRealmInstance()!!.where(Article::class.java).findAll()
        return articleList
    }

    companion object {
        private var appDatabase: AppDatabase? = null

        /**
         * Gets instance.
         *
         * @return the instance
         */
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