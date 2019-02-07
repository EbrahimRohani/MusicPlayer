package com.example.ebrah.musicplayer.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ebrah.musicplayer.R;

public class PlayMusicActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_music);

        mSongString= getIntent().getStringExtra(EXTRA_SONG_STRING);
        mAdapterPosition = getIntent().getIntExtra(EXTRA_ADAPTER_POSITION, 0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        PlayMusicFragment playMusicFragment = PlayMusicFragment.newInstance(mSongString, mAdapterPosition);
        if(fragmentManager.findFragmentById(R.id.single_play_music_container) == null)
            fragmentManager.beginTransaction().add(R.id.single_play_music_container, playMusicFragment).commit();
    }

}
