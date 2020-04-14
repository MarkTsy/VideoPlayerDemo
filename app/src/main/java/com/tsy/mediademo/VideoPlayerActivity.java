package com.tsy.mediademo;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


public class VideoPlayerActivity extends AppCompatActivity {

    private VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);



        init();

    }
    private void init() {

        vv = findViewById(R.id.vv);
        vv.setVideoURI(Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + R.raw.test_video));
    }

    public void play(View view) {
        vv.start();
    }

    public void pause(View view) {
        vv.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
