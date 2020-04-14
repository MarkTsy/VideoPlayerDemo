package com.tsy.mediademo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

public class SurfaceActivity extends AppCompatActivity {

    private SurfaceView sfv;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface);

        sfv = findViewById(R.id.sfv);
        mp = new MediaPlayer();

        try {
            mp.setDataSource(this, Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + R.raw.test_video));
            sfv.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    mp.setDisplay(holder);
                }
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                }
            });
            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            mp.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play(View view) {
        mp.start();
    }

    public void pause(View view) {
        mp.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
}
