package com.example.ebrah.musicplayer.model.MainPlayer;

import android.media.MediaPlayer;
import android.util.Log;

import com.example.ebrah.musicplayer.database.App;
import com.example.ebrah.musicplayer.model.Song.Song;

import java.io.IOException;

public class MainPlayer {

    private static final MainPlayer ourInstance = new MainPlayer();
    public static final String TAG = "noober";
    private MediaPlayer mMediaPlayer;

    private MainPlayer() {
        if (ourInstance == null)
            mMediaPlayer = new MediaPlayer();
    }

    public static MainPlayer getInstance() {
        return ourInstance;
    }


    public MediaPlayer getMediaPlayer(){
        return mMediaPlayer;
    }

    private void songPrepare(Song song) throws IOException {
        mMediaPlayer.reset();
        mMediaPlayer.setDataSource(App.getApp().getApplicationContext(), song.getSongUri());
        mMediaPlayer.prepare();
    }

    public void play(Song song)  {
        try {
            mMediaPlayer.setDataSource(App.getApp().getApplicationContext(), song.getSongUri());
            mMediaPlayer.prepare();
        } catch (Exception e) {
            Log.d(TAG, "play: " , e);
        }
        mMediaPlayer.start();
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public int getCurrentDuration() {
        return mMediaPlayer.getCurrentPosition();
    }

    public void seekTo(int msec) {
        mMediaPlayer.seekTo(msec);
    }

    public void pause() {
        mMediaPlayer.pause();
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    public void next(Song song) {
        try {
            songPrepare(song);
            play(song);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void setDataSource(Song song){
        try {
            mMediaPlayer.setDataSource(App.getApp().getApplicationContext(), song.getSongUri());
        } catch (Exception e) {
            Log.e(TAG, "setDataSource: ", e );
        }
    }

    public void reset(){
        mMediaPlayer.reset();
    }

    public void prepare(){
        try {
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void release(){
        mMediaPlayer.release();
    }

    public void stop() {
        mMediaPlayer.stop();
    }

}
