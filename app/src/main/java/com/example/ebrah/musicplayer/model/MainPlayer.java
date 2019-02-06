package com.example.ebrah.musicplayer.model;

import android.media.MediaPlayer;

import com.example.ebrah.musicplayer.database.App;

import java.io.IOException;

public class MainPlayer {

    private static final MainPlayer ourInstance = new MainPlayer();
    private MediaPlayer mMediaPlayer;

    private Song mSong;

    private MainPlayer() {
        if (ourInstance == null)
            mMediaPlayer = new MediaPlayer();
    }

    public static MainPlayer getInstance() {
        return ourInstance;
    }

    public MainPlayer getMediaPlayer(Song song) {
        this.mSong = song;

        try {
            songPrepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    private void songPrepare() throws IOException {
        mMediaPlayer.reset();
        mMediaPlayer.setDataSource(App.getApp().getApplicationContext(), mSong.getSongUri());
        mMediaPlayer.prepare();
    }

    public void play() {
        mMediaPlayer.start();
    }

    public int songDuration() {
        return mMediaPlayer.getDuration();
    }

    public int songCurrentDuration() {
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

    public void next(Song nextSong) {
        this.mSong = nextSong;
        try {
            songPrepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        play();
    }
}
