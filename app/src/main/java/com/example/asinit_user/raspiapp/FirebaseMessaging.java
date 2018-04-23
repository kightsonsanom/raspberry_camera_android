package com.example.asinit_user.raspiapp;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;


public class FirebaseMessaging extends FirebaseMessagingService {

    BucketManager bucketManager;

    @Override
    public void onCreate() {
        super.onCreate();
        bucketManager = ((App)getApplicationContext()).getBucketManager();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getNotification() != null) {
            String body = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Body: " + body);
//            zapisywanie nazwy zdjęcia do pamięci aplikacji
            bucketManager.saveFilename(body);
//            pobieranie zdjęcia z serwera na podstawie przesłanej nazwy
            bucketManager.fetchData();
        }
    }
}
