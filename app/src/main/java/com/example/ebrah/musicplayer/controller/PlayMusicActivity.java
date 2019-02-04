package com.example.ebrah.musicplayer.controller;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PlayMusicActivity extends SingleFragmentActivity {

    public static final String EXTRA_SONG_STRING = "com.example.ebrah.musicplayer.controller.song_string";
    public String mSongString;

    public static Intent newIntent (Context context, String songUri){
        Intent intent = new Intent(context, PlayMusicActivity.class);
        intent.putExtra(EXTRA_SONG_STRING, songUri);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSongString= getIntent().getStringExtra(EXTRA_SONG_STRING);
        Log.i("loleri", "onCreate: Activity = " + mSongString);
    }

    @Override
    public Fragment createFragment() {
        return PlayMusicFragment.newInstance(mSongString);
    }


}
