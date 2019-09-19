package pl.tolichwer.raspiapp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import pl.tolichwer.raspiapp.R;


public class MainActivity extends AppCompatActivity implements BucketCallback {

    private BucketManager bucketManager;
    ProgressBar progressBar;
    TextView failTextView;
    Button btnShowPhoto;

    public static final String BUCKET_NAME = "raspi-261ac.appspot.com";

    @Override
    protected void onStart() {
        super.onStart();
        bucketManager.setBucketCallback(this);
        bucketManager.saveFilename("fejs");
    }

    @Override
    protected void onStop() {
        super.onStop();
        bucketManager.setBucketCallback(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progress_bar);
        failTextView = findViewById(R.id.textview);
        btnShowPhoto = findViewById(R.id.button);
        bucketManager = ((App) getApplicationContext()).getBucketManager();
        bucketManager.setBucketCallback(this);

        btnShowPhoto.setOnClickListener(v -> {
            btnShowPhoto.setVisibility(View.GONE);
            bucketManager.fetchData();
        });
    }


    @Override
    public void showImage(Bitmap bitmap) {
        failTextView.setVisibility(View.GONE);
        btnShowPhoto.setVisibility(View.VISIBLE);
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void showFailed() {
        failTextView.setVisibility(View.VISIBLE);

    }
}
