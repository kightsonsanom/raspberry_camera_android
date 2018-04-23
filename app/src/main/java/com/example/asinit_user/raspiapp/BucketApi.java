package com.example.asinit_user.raspiapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class BucketApi {

    private Storage storage;

    public BucketApi(Context context) {
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open("credentials.json", Context.MODE_WORLD_READABLE);
        storage = StorageOptions.newBuilder()
                .setProjectId("raspi-261ac")
                .setCredentials(ServiceAccountCredentials.fromStream(inputStream))
                .build()
                .getService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Observable<Bucket> getBucketObsevable(String bucketName) {
        return Observable.create(bucketObservable -> {
            Bucket bucket = storage.get(bucketName);
            bucketObservable.onNext(bucket);
            bucketObservable.onComplete();
        });
    }

    public Observable<Bitmap> getImage(Bucket bucket, String fileName) {
        return Observable.create(bucketObservable -> {
            byte[] bytes = bucket.get(fileName).getContent();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            bucketObservable.onNext(bitmap);
            bucketObservable.onComplete();
        });
    }

    Observable<List<String>> getBucketList(){
        return  Observable.create(bucketObservable -> {
            List<String> bucketList = new ArrayList<>();
            for (Bucket b : storage.list().iterateAll()) {
                bucketList.add(b.toString());
            }
            bucketObservable.onNext(bucketList);
            bucketObservable.onComplete();
        });
    }
}
