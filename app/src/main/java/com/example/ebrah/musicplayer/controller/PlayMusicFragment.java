package com.example.ebrah.musicplayer.controller;


import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ebrah.musicplayer.model.MainPlayer;
import com.example.ebrah.musicplayer.R;
import com.example.ebrah.musicplayer.model.Song;
import com.example.ebrah.musicplayer.model.SongLab;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayMusicFragment extends Fragment {


    public static final String ARGS_ADAPTER_POSITION = "args_adapter_position";
    private static final String ARGS_SONG_STRING = "args_song_string";
    private static final String FORMAT = "%02d:%02d:%02d";
    private MainPlayer mMainPlayer;

    private CountDownTimer mCountDownTimer;
    private int[] mRepeatStateArray;
    private int mResumeSongCurrentPosition;
    private Uri mSongUri;
    private boolean isShuffleBtnClicked = false;
    private ImageButton mPlayBtn, mNextBtn, mShuffleBtn, mPreviousBtn, mRepeatBtn;
    private ImageView mAlbumCover;
    private SeekBar mMusicSeekBar;
    private TextView mSongTitle, mTimerSeekBar, mCountDownSeekBar;
    private MediaPlayer mMusicPlayer;
    private int mAdapterPosition;
    private Song mSong;
    private int mPrevPosition;
    private int mPrevCounter;
    private int mRepeatState;
    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mMusicSeekBar.setProgress(mMainPlayer.getCurrentDuration());
            mTimerSeekBar.setText(milliSecondsToTimer(mMainPlayer.getCurrentDuration()));
            mCountDownTimer.onFinish();
            counterDownTimer(mMainPlayer.getDuration() - mMainPlayer.getCurrentDuration(), mCountDownSeekBar);
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
        mRepeatStateArray = new int[]{0, 1, 2};
        mRepeatState = 0;
        mMainPlayer = MainPlayer.getInstance();
        counterDownTimer(mMainPlayer.getDuration() - mMainPlayer.getCurrentDuration(), mCountDownSeekBar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_play_music, container, false);
        initialize(view);

        setSongInfo();
//        if (mSong.getSongImageUri() != null)
//            mAlbumCover.setImageURI(mSong.getSongImageUri());

        mMainPlayer.play(mSong);
        setSongInfo();

        mMusicSeekBar.setMax(mSong.getSongDuration());
        mHandler.postDelayed(runnable, 1000);

        mMusicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mMainPlayer.seekTo(progress);
                    mTimerSeekBar.setText(milliSecondsToTimer(mMainPlayer.getCurrentDuration()));
                    mCountDownTimer.onFinish();
                    counterDownTimer(mMainPlayer.getDuration() - mMainPlayer.getCurrentDuration(), mCountDownSeekBar);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mMainPlayer.getMediaPlayer().setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                switch (mRepeatState){
                    case 0:
                        if(mAdapterPosition == SongLab.getInstance().getSongList(getActivity()).size()-1){
                            Log.i("loperi", "onCompletion: REACHED! if..." + mAdapterPosition +"\t" +  mRepeatState);
                            mAdapterPosition = -1;
                            mSong = SongLab.getInstance().getSongList(getActivity()).get(++mAdapterPosition);
                            mMainPlayer.stop();
                            mMusicSeekBar.setMax(mSong.getSongDuration());
                            mMusicSeekBar.setProgress(0);
                            setSongInfo();
                        }
                        else {
                            Log.i("loperi", "onCompletion: REACHED else....!" + mAdapterPosition + "\t" + mRepeatState);
                            goToNextMusic();
                        }
                        break;

                    case 1:
                        Log.i("loperi", "onCompletion: REACHED!" + mAdapterPosition +"\t" +  mRepeatState);
                        if(mAdapterPosition == SongLab.getInstance().getSongList(getActivity()).size()-1)
                            mAdapterPosition = -1;
                        goToNextMusic();
                        break;

                    case 2:
                        Log.i("loperi", "onCompletion: REACHED!" + mAdapterPosition +"\t" +  mRepeatState);
                        loadMusic();
                        break;

                }

            }
        });

        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if (mMainPlayer.isPlaying()) {
                mMainPlayer.pause();
                mPlayBtn.setImageResource(R.drawable.ic_play_music);
                mResumeSongCurrentPosition = mMainPlayer.getCurrentDuration();
                mCountDownTimer.onFinish();
            } else {
                mMainPlayer.seekTo(mResumeSongCurrentPosition);
                mMainPlayer.play(mSong);
                mPlayBtn.setImageResource(R.drawable.ic_pause_music);
            }

            }
        });

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPlayer.pause();
                //mPrevCounter = 0;
                if(isShuffleBtnClicked) {
                    Random random = new Random();
                    int shuffledPosition = random.nextInt(SongLab.getInstance().getSongList(getActivity()).size());
                    mSong = SongLab.getInstance().getSongList(getActivity()).get(shuffledPosition);
                    mAdapterPosition = shuffledPosition;

                }

                else {
                    if(mAdapterPosition == SongLab.getInstance().getSongList(getActivity()).size()-1)
                        mAdapterPosition = -1;
                    mSong = SongLab.getInstance().getSongList(getActivity()).get(++mAdapterPosition);
                        //mPrevPosition = mAdapterPosition;
                }
                loadMusic();
            }
        });

        mPreviousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAdapterPosition == 0){
                    mAdapterPosition = SongLab.getInstance().getSongList(getActivity()).size();
                }
                if(mMainPlayer.getCurrentDuration()< 2000) {
//                    if (mPrevCounter == 0) {
//                        mPrevPosition++;
//                        mPrevCounter++;
//                    }
                    mMainPlayer.pause();
                    mSong = SongLab.getInstance().getSongList(getActivity()).get(--mAdapterPosition);
                    loadMusic();
                }
                else {
                    loadMusic();
                }

            }
        });

        mShuffleBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(isShuffleBtnClicked)
                    mShuffleBtn.clearColorFilter();
                else
                    mShuffleBtn.setColorFilter(getResources().getColor(R.color.colorPrimaryLight));
                setShuffleBtnClicked();
            }
        });

        mRepeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mRepeatState == 2)
                    mRepeatState = -1;
                int state = mRepeatStateArray[++mRepeatState];
                setRepeatState(state);
            }
        });

        return view;
    }

    private void goToNextMusic() {
        if(isShuffleBtnClicked) {
            Random random = new Random();
            int shuffledPosition = random.nextInt(SongLab.getInstance().getSongList(getActivity()).size());
            mSong = SongLab.getInstance().getSongList(getActivity()).get(shuffledPosition);
            mAdapterPosition = shuffledPosition;

        }

        else {
            if(mAdapterPosition == SongLab.getInstance().getSongList(getActivity()).size()-1)
                mAdapterPosition = -1;
            mSong = SongLab.getInstance().getSongList(getActivity()).get(++mAdapterPosition);
            //mPrevPosition = mAdapterPosition;
        }
        loadMusic();
    }

    private void loadMusic() {
        mMusicSeekBar.setMax(mSong.getSongDuration());
        mMainPlayer.next(mSong);
        setSongInfo();
    }

    private void setSongInfo() {
        if(mSong.getSongImageUri() == null)
            mAlbumCover.setImageResource(R.drawable.ic_all_musics);
        else
            mAlbumCover.setImageURI(mSong.getSongImageUri());
        mSongTitle.setText(mSong.getTitle());

        if(mMainPlayer.isPlaying())
            mPlayBtn.setImageResource(R.drawable.ic_pause_music);
        else
            mPlayBtn.setImageResource(R.drawable.ic_play_music);

        mTimerSeekBar.setText(milliSecondsToTimer(mMainPlayer.getCurrentDuration()));
        mCountDownTimer.onFinish();
        counterDownTimer(mMainPlayer.getDuration() - mMainPlayer.getCurrentDuration(), mCountDownSeekBar);

    }

    private void setRepeatState(int repeatState){
        if(repeatState == 0){
            mRepeatBtn.setImageResource(R.drawable.ic_repeat_music);
            mRepeatBtn.clearColorFilter();
        }else if(repeatState == 1){
            mRepeatBtn.setImageResource(R.drawable.ic_repeat_music);
            mRepeatBtn.setColorFilter(getResources().getColor(R.color.colorPrimaryLight));
        }else{
            mRepeatBtn.setImageResource(R.drawable.ic_repeat_once_music);
            mRepeatBtn.setColorFilter(getResources().getColor(R.color.colorPrimaryLight));
        }

    }

    private void initialize(View view) {
        mPlayBtn = view.findViewById(R.id.play_button);
        mNextBtn = view.findViewById(R.id.next_button);
        mMusicSeekBar = view.findViewById(R.id.music_seek_bar);
        mAlbumCover = view.findViewById(R.id.music_single_album_cover);
        mShuffleBtn = view.findViewById(R.id.shuffle_button_unselected);
        mPreviousBtn = view.findViewById(R.id.previous_button);
        mSongTitle = view.findViewById(R.id.song_title_single_play_music_frag);
        mRepeatBtn = view.findViewById(R.id.repeat_button);
        mTimerSeekBar = view.findViewById(R.id.timer_seek_bar);
        mCountDownSeekBar = view.findViewById(R.id.count_down_timer_seek_bar);
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

    private String milliSecondsToTimer(long milliseconds){
        String hoursTimerString ;
        String secondsString ;
        String minutesString ;

        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);


        hoursTimerString = hours < 10 ? "0" + hours : hours + "";
        minutesString = minutes < 10 ? "0" + minutes : minutes + "";
        secondsString = seconds < 10 ? "0" + seconds : seconds + "";

        if(mMainPlayer.getDuration() < 3600*1000)
            return minutesString + ":" + secondsString;
        else
            return hoursTimerString + ":" + minutesString + ":" + secondsString;
    }

    private String milliSecondsToCountDownTimer(long  totalDuration, long milliseconds){

        String hoursString ;
        String secondsString ;
        String minutesString ;

        int hours = (int) (totalDuration / 1000*60*60) - (int)( milliseconds / (1000*60*60));
        int minutes = (int)(totalDuration % (1000*60*60)) / (1000*60) - (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((totalDuration % (1000*60*60)) % (1000*60) / 1000) -(int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);


        hoursString = hours < 10 ? "0" + hours : hours + "";
        minutesString = minutes < 10 ? "0" + minutes : minutes + "";
        secondsString = seconds < 10 ? "0" + seconds : seconds + "";

        if(mMainPlayer.getDuration() < 3600*1000)
            return minutesString + ":" + secondsString;
        else
            return hoursString + ":" + minutesString + ":" + secondsString;
    }

    private void counterDownTimer(long remainingDuration, final TextView tv){

        mCountDownTimer = new CountDownTimer(remainingDuration, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                tv.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                this.cancel();
            }
        }.start();

    }

}
