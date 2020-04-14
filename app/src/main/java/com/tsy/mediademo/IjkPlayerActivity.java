package com.tsy.mediademo;

import androidx.appcompat.app.AppCompatActivity;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.misc.IMediaDataSource;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class IjkPlayerActivity extends AppCompatActivity {

    private SurfaceView sfv;
    private IjkMediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijk_player);

        sfv = findViewById(R.id.sfv);
        sfv.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                createPlayer();
                mPlayer.setDisplay(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if(holder != null) holder.removeCallback(this);
            }
        });
    }

    private void createPlayer() {
        if(mPlayer == null) {
            mPlayer = new IjkMediaPlayer();
            //mPlayer.setDataSource(this,  Uri.parse("android.resource://" + this.getPackageName() + "/raw/" + R.raw.test_video));
            mPlayer.setDataSource(new RawDataSourceProvider(getResources().openRawResourceFd(R.raw.test_video)));
            mPlayer.prepareAsync();
        }
    }

    public void play(View view) {
        mPlayer.start();
    }

    public void pause(View view) {
        mPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.stop();
    }

    public static class RawDataSourceProvider implements IMediaDataSource {
        private AssetFileDescriptor mDescriptor;

        private byte[]  mMediaBytes;

        public RawDataSourceProvider(AssetFileDescriptor descriptor) {
            this.mDescriptor = descriptor;
        }

        @Override
        public int readAt(long position, byte[] buffer, int offset, int size) throws IOException {
            if(position + 1 >= mMediaBytes.length){
                return -1;
            }

            int length;
            if(position + size < mMediaBytes.length){
                length = size;
            }else{
                length = (int) (mMediaBytes.length - position);
                if(length > buffer.length)
                    length = buffer.length ;

                length--;
            }
            System.arraycopy(mMediaBytes, (int) position, buffer, offset, length);

            return length;
        }

        @Override
        public long getSize() throws IOException {
            long length  = mDescriptor.getLength();
            if(mMediaBytes == null){
                InputStream inputStream = mDescriptor.createInputStream();
                mMediaBytes = readBytes(inputStream);
            }


            return length;
        }

        @Override
        public void close() throws IOException {
            if(mDescriptor != null)
                mDescriptor.close();

            mDescriptor = null;
            mMediaBytes = null;
        }

        private byte[] readBytes(InputStream inputStream) throws IOException {
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            return byteBuffer.toByteArray();
        }

        public static RawDataSourceProvider create(Context context, Uri uri){
            try {
                AssetFileDescriptor fileDescriptor = context.getContentResolver().openAssetFileDescriptor(uri, "r");
                return new RawDataSourceProvider(fileDescriptor);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
