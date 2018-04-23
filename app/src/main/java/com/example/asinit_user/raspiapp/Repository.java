package com.example.asinit_user.raspiapp;

import android.content.SharedPreferences;

public class Repository {

    private static final String FILENAME = "FILENAME";
    private SharedPreferences preferences;

    public Repository(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void saveFilename(String filename) {
        preferences.edit().putString(FILENAME, filename).apply();
    }

    public String getFilename() {
        return preferences.getString(FILENAME, "");
    }
}
