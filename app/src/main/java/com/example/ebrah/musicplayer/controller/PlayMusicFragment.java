package com.example.ebrah.musicplayer.controller;


import android.annotation.SuppressLint;
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

    private int mSongCurrentPosition;
    private Uri mSongUri;
    private boolean isShuffleBtnClicked = false;
    private ImageButton mPlayBtn, mNextBtn, mShuffleBtn, mPreviousBtn;
    private ImageView mAlbumCover;
    private SeekBar mMusicSeekBar;
    private int mAdapterPosition;
    private Song mSong;
    private int mPrevPosition;
    private int mPrevCounter;
    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mMusicSeekBar.setProgress(mMainPlayer.getCurrentDuration());
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

        mMainPlayer = MainPlayer.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_play_music, container, false);
        initialize(view);

        if (mSong.getSongImageUri() != null)
            mAlbumCover.setImageURI(mSong.getSongImageUri());

        mMainPlayer.play(mSong);
        mPlayBtn.setImageResource(R.drawable.ic_pause_music);


        mMusicSeekBar.setMax(mSong.getSongDuration());
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
                mSongCurrentPosition = mMainPlayer.getCurrentDuration();
            } else {
                mMainPlayer.seekTo(mSongCurrentPosition);
                mMainPlayer.play(mSong);
                mPlayBtn.setImageResource(R.drawable.ic_pause_music);
            }

            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPlayer.pause();
                mPrevCounter = 0;
                if(isShuffleBtnClicked) {
                    Random random = new Random();
                    int shuffledPosition = random.nextInt(SongLab.getInstance().getSongList(getActivity()).size());
                    mSong = SongLab.getInstance().getSongList(getActivity()).get(shuffledPosition);
                    mPrevPosition = mAdapterPosition;
                    mAdapterPosition = shuffledPosition;

                }

                else {
                    mSong = SongLab.getInstance().getSongList(getActivity()).get(++mAdapterPosition);
                    mPrevPosition = mAdapterPosition;
                }
                mMusicSeekBar.setMax(mSong.getSongDuration());
                mMainPlayer.next(mSong);
                setSongCover();
            }
        });

        mPreviousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPrevCounter == 0){
                    mPrevPosition++;
                    mPrevCounter ++;
                }
                mMainPlayer.pause();
                mSong = SongLab.getInstance().getSongList(getActivity()).get(--mPrevPosition);
                mMusicSeekBar.setMax(mSong.getSongDuration());
                mMainPlayer.next(mSong);
                mAdapterPosition = mPrevPosition;

                setSongCover();
            }
        });

        mShuffleBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(isShuffleBtnClicked)
                    mShuffleBtn.clearColorFilter();
                else
                    mShuffleBtn.setColorFilter(getResources().getColor(R.color.colorAccent));
                setShuffleBtnClicked();
            }
        });

        return view;
    }

    private void setSongCover() {
        if(mSong.getSongImageUri() == null)
            mAlbumCover.setImageResource(R.drawable.ic_all_musics);
        else
            mAlbumCover.setImageURI(mSong.getSongImageUri());
    }

    private void initialize(View view) {
        mPlayBtn = view.findViewById(R.id.play_button);
        mNextBtn = view.findViewById(R.id.next_button);
        mMusicSeekBar = view.findViewById(R.id.music_seek_bar);
        mAlbumCover = view.findViewById(R.id.music_single_album_cover);
        mShuffleBtn = view.findViewById(R.id.shuffle_button_unselected);
        mPreviousBtn = view.findViewById(R.id.previous_button);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runnable);
        mMainPlayer.release();
    }

    public void setShuffleBtnClicked(){
        if(isShuffleBtnClicked)
            isShuffleBtnClicked = false;
        else
            isShuffleBtnClicked = true;
    }
}
