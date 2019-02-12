package com.example.ebrah.musicplayer.controller.SinglePlayMusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.ebrah.musicplayer.R;

public class SinglePlayMusicActivity extends AppCompatActivity {

    private static final String EXTRA_SONG_STRING = "com.example.ebrah.musicplayer.controller.song_string";
    private static final String EXTRA_ADAPTER_POSITION = "com.example.ebrah.musicplayer.controller.adapter_position";
    public static final String EXTRA_ALBUM_ID = "com.example.ebrah.musicplayer.controller.extra_album_id";

    public static Intent newIntent (Context context, String songUri, int adapterPosition, Long albumId){
        Intent intent = new Intent(context, SinglePlayMusicActivity.class);
        intent.putExtra(EXTRA_SONG_STRING, songUri);
        intent.putExtra(EXTRA_ADAPTER_POSITION, adapterPosition);
        intent.putExtra(EXTRA_ALBUM_ID,albumId );
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_play_music);

        String songString= getIntent().getStringExtra(EXTRA_SONG_STRING);
        int adapterPosition = getIntent().getIntExtra(EXTRA_ADAPTER_POSITION, 0);
        Long albumId = (Long) getIntent().getSerializableExtra(EXTRA_ALBUM_ID);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SinglePlayMusicFragment singlePlayMusicFragment = SinglePlayMusicFragment.newInstance(songString, adapterPosition, albumId);
        if(fragmentManager.findFragmentById(R.id.single_play_music_container) == null)
            fragmentManager.beginTransaction().add(R.id.single_play_music_container, singlePlayMusicFragment).commit();
    }

}
