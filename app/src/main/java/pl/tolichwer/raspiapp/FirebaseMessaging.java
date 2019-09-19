package pl.tolichwer.raspiapp;

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
