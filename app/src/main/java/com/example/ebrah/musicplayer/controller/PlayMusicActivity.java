package com.example.ebrah.musicplayer.controller;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PlayMusicActivity extends SingleFragmentActivity {

    private static final String EXTRA_SONG_STRING = "com.example.ebrah.musicplayer.controller.song_string";
    private static final String EXTRA_ADAPTER_POSITION = "com.example.ebrah.musicplayer.controller.adapter_position";
    private String mSongString;
    private int mAdapterPosition;

    public static Intent newIntent (Context context, String songUri, int adapterPosition){
        Intent intent = new Intent(context, PlayMusicActivity.class);
        intent.putExtra(EXTRA_SONG_STRING, songUri);
        intent.putExtra(EXTRA_ADAPTER_POSITION, adapterPosition);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        mSongString= getIntent().getStringExtra(EXTRA_SONG_STRING);
        mAdapterPosition = getIntent().getIntExtra(EXTRA_ADAPTER_POSITION, 0);
        return PlayMusicFragment.newInstance(mSongString, mAdapterPosition);
    }


}
