package com.example.asinit_user.raspiapp;

import android.app.Application;
import android.preference.PreferenceManager;

public class App extends Application {

    private BucketApi bucketApi;
    private BucketManager bucketManager;
    private Repository repository;
    @Override
    public void onCreate() {
        super.onCreate();
        bucketApi = new BucketApi(getApplicationContext());
        repository = new Repository(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        bucketManager = new BucketManager(bucketApi, repository);
    }

    public BucketManager getBucketManager() {
        return bucketManager;
    }
}
