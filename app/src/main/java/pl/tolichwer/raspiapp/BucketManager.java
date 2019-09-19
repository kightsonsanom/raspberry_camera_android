package pl.tolichwer.raspiapp;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.cloud.storage.Bucket;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BucketManager {

    private BucketApi bucketApi;
    private BucketCallback bucketCallback;
    private Bucket bucket;
    private Repository repository;

    public BucketManager(BucketApi bucketApi, Repository repository) {
        this.bucketApi = bucketApi;
        this.repository = repository;
    }

    public void setBucketCallback(BucketCallback bucketCallback) {
        this.bucketCallback = bucketCallback;
    }

    public void saveFilename(String filename) {
        repository.saveFilename(filename);
    }

    public String getFilename() {
        return repository.getFilename();
    }

    public void getBucket() {
        bucketApi.getBucketObsevable(MainActivity.BUCKET_NAME).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bucket>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(Bucket b) {
                        bucket = b;
                        getImage(bucket);
                    }
                    @Override
                    public void onError(Throwable e) {
//                        bucketCallback.showProgress(false);
                        bucketCallback.showFailed();
                    }

                    @Override
                    public void onComplete() {
//                        bucketCallback.showProgress(false);
                    }
                });
    }

    public void getImage(Bucket b) {
        bucketApi.getImage(b, getFilename()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(Bitmap bitmap) {
                        bucketCallback.showImage(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
//                        bucketCallback.showProgress(false);
                        bucketCallback.showFailed();
                    }
                    @Override
                    public void onComplete() {
//                        bucketCallback.showProgress(false);
                    }
                });
    }


    public void getBucketList(){
        bucketApi.getBucketList().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<String> strings) {
                        for (String s: strings) {
                            Log.d("BUCKET LIST", "bucket: " + s);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void fetchData() {
        if (bucket == null) {
            getBucket();
        } else {
            getImage(bucket);
        }
    }
}
