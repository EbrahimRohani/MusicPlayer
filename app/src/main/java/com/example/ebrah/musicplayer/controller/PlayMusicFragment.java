package com.example.ebrah.musicplayer.controller;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.example.ebrah.musicplayer.model.MainPlayer;
import com.example.ebrah.musicplayer.R;
import com.example.ebrah.musicplayer.model.Song;
import com.example.ebrah.musicplayer.model.SongLab;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayMusicFragment extends Fragment {


    public static final String ARGS_ADAPTER_POSITION = "args_adapter_position";
    private static final String ARGS_SONG_STRING = "args_song_string";
    private MainPlayer mMainPlayer;

    private Uri mSongUri;
    private ImageButton mPlayBtn, mNextBtn, mShuffleButton;
    private ImageView mAlbumCover;
    private MediaPlayer mMediaPlayer;
    private SeekBar mMusicSeekBar;
    private int mAdapterPosition;
    private Song mSong;
    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mMusicSeekBar.setProgress(mMainPlayer.songCurrentDuration());
            mHandler.postDelayed(this, 1000);
        }
    };


    public PlayMusicFragment() {

    }

    public static PlayMusicFragment newInstance(String songString, int adapterPosition) {

        Bundle args = new Bundle();
        args.putString(ARGS_SONG_STRING, songString);
        args.putInt(ARGS_ADAPTER_POSITION, adapterPosition);
        PlayMusicFragment fragment = new PlayMusicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String songString = getArguments().getString(ARGS_SONG_STRING);
        mAdapterPosition = getArguments().getInt(ARGS_ADAPTER_POSITION);
        mSongUri = Uri.parse(songString);
        mSong = SongLab.getInstance().getSongByUri(getActivity(), mSongUri);

        mMainPlayer = MainPlayer.getInstance().getMediaPlayer(mSong);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_music, container, false);
        initialize(view);

        if (mSong.getSongImageUri() != null)
            mAlbumCover.setImageURI(mSong.getSongImageUri());


        mMainPlayer.play();
        mPlayBtn.setImageResource(R.drawable.ic_pause_music);

        mMusicSeekBar.setMax(mMainPlayer.songDuration());
        mHandler.postDelayed(runnable, 1000);

        mMusicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    mMainPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mMainPlayer.isPlaying()) {
                    mMainPlayer.pause();
                    mPlayBtn.setImageResource(R.drawable.ic_play_music);
                } else {
                    mMainPlayer.play();
                    mPlayBtn.setImageResource(R.drawable.ic_pause_music);
                }

            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPlayer.pause();
                mSong = SongLab.getInstance().getSongList(getActivity()).get(++mAdapterPosition);
                mMainPlayer.next(mSong);
                if(mSong.getSongImageUri() == null)
                    mAlbumCover.setImageResource(R.drawable.ic_all_musics);
                else
                    mAlbumCover.setImageURI(mSong.getSongImageUri());
            }
        });

        mShuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShuffleButton.isEnabled()) {
                    mShuffleButton.setColorFilter(R.color.design_default_color_primary);
                } else {

                }
                Random random = new Random();
                int value = random.nextInt(SongLab.getInstance().getSongList(getActivity()).size());
                mSong = SongLab.getInstance().getSongList(getActivity()).get(value);
            }
        });

        return view;
    }

    private void initialize(View view) {
        mPlayBtn = view.findViewById(R.id.play_button);
        mNextBtn = view.findViewById(R.id.next_button);
        mMusicSeekBar = view.findViewById(R.id.music_seek_bar);
        mAlbumCover = view.findViewById(R.id.music_single_album_cover);
        mShuffleButton = view.findViewById(R.id.shuffle_button_unselected);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }
}
