package com.tsy.mediademo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class ExoPlayerActivity extends AppCompatActivity {

    private PlayerView pv;
    SimpleExoPlayer simpleExoPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);

        pv = findViewById(R.id.pv);

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        pv.setPlayer(simpleExoPlayer);

        Uri uri =  Uri.parse("rawresource:///" + R.raw.test_video);

        DataSource.Factory factory = new DefaultDataSourceFactory(this, "exo-player");

        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(factory).createMediaSource(uri);

        simpleExoPlayer.prepare(mediaSource);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.stop();
    }
}
