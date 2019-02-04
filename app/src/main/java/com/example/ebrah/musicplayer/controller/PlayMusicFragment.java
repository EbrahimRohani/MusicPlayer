package com.example.ebrah.musicplayer.controller;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ebrah.musicplayer.R;

public class PlayMusicFragment extends Fragment {


    private static final String ARGS_SONG_STRING = "args_song_string";

    private Uri mSongUri;
    private ImageButton mPlayBtn;
    private MediaPlayer mMediaPlayer;


    public static PlayMusicFragment newInstance(String songString) {

        Bundle args = new Bundle();
        args.putString(ARGS_SONG_STRING, songString);
        PlayMusicFragment fragment = new PlayMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public PlayMusicFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String songString = (String) getArguments().get(ARGS_SONG_STRING);
        Log.i("loleri", "onCreate: , Fragment = " + songString);
        mSongUri = Uri.parse(songString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music, container, false);
        initialize(view);



        mMediaPlayer = MediaPlayer.create(getActivity(), mSongUri);

        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaPlayer.start();
            }
        });
        return view;
    }

    private void initialize(View view){
        mPlayBtn = view.findViewById(R.id.play_button);
    }

}
