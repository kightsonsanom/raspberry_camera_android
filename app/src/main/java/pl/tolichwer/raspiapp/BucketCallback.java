package pl.tolichwer.raspiapp;

import android.graphics.Bitmap;

public interface BucketCallback {
    void showImage(Bitmap bitmap);

//    void showProgress(boolean enable);

    void showFailed();
}
