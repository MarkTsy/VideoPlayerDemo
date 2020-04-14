package com.tsy.mediademo;

import androidx.appcompat.app.AppCompatActivity;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void VideViewPlay(View view) {
        startActivity(new Intent(this, VideoPlayerActivity.class));
    }

    public void SurfaceViewPlay(View view) {
        startActivity(new Intent(this, SurfaceActivity.class));
    }

    public void ExoPlay(View view) {
        startActivity(new Intent(this, ExoPlayerActivity.class));
    }

    public void IjkPlayer(View view) {
        startActivity(new Intent(this, IjkPlayerActivity.class));
    }
}
