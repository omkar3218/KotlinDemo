package com.omkar.jet2demo.data.local;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmModel;


public class AppDatabase {

    private static AppDatabase dbHelper;
    private Realm realmInstance;

    private AppDatabase() {
        prepareRealmInstance();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AppDatabase getInstance() {
        if (dbHelper == null) dbHelper = new AppDatabase();
        return dbHelper;
    }

    /**
     * Gets realm instance.
     *
     * @return the realm instance
     */
    private Realm getRealmInstance() {
        openDB();
        return realmInstance;
    }

    private void openDB() {
        if (realmInstance == null || realmInstance.isClosed())
            // Get mapView Realm instance for this thread with applied configuration
            realmInstance = Realm.getDefaultInstance();
    }

    private void prepareRealmInstance() {
        RealmConfiguration config = new RealmConfiguration.Builder().name("article.realm")
                .schemaVersion(1)
                .build();

        Realm.setDefaultConfiguration(config);
        Realm.compactRealm(config);

        openDB();
    }

    void closeDB() {
        if (realmInstance != null) realmInstance.close();
    }


    private void beginTransaction() {
        getRealmInstance().beginTransaction();
    }

    private void commitTransaction() {
        getRealmInstance().commitTransaction();
        Realm.compactRealm(Realm.getDefaultConfiguration());
    }

    public void saveArticleDataModel(RealmModel realmModel) {
        try {
            getRealmInstance().beginTransaction();
            getRealmInstance().copyToRealmOrUpdate(realmModel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            commitTransaction();
        }
    }


}