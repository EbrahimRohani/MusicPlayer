package com.example.ebrah.musicplayer.controller.AlbumsBottomNav;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ebrah.musicplayer.R;
import com.example.ebrah.musicplayer.controller.SinglePlayMusic.SinglePlayMusicActivity;
import com.example.ebrah.musicplayer.model.Album.Album;
import com.example.ebrah.musicplayer.model.Album.AlbumLab;
import com.example.ebrah.musicplayer.model.Song.Song;
import com.example.ebrah.musicplayer.model.Song.SongLab;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongsOfAlbumFragment extends Fragment {

    public static final String ARGS_ALBUM_ID = "args_album_id";
    private RecyclerView mRecyclerView;
    private ImageView mAlbumCoverImageView, mArtistCoverImageView;
    private TextView mAlbumTitleTextView, mAlbumArtistTextView;
    private SongAdapter mSongAdapter;
    private Long mAlbumId;
    private Album mAlbum;


    public static SongsOfAlbumFragment newInstance(Long albumId) {

        Bundle args = new Bundle();
        args.putLong(ARGS_ALBUM_ID, albumId);
        SongsOfAlbumFragment fragment = new SongsOfAlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public SongsOfAlbumFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbumId = getArguments().getLong(ARGS_ALBUM_ID);
        mAlbum = AlbumLab.getInstance().getAlbumById(getActivity(), mAlbumId, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_songs_of_album, container, false);
        initialize(view);
        mAlbumCoverImageView.setImageURI(mAlbum.getAlbumCoverUri());
        mAlbumTitleTextView.setText(mAlbum.getName());
        mArtistCoverImageView.setImageResource(R.drawable.ic_all_musics);
        mAlbumArtistTextView.setText(mAlbum.getArtistName());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    public void updateUI(){
        List<Song> songList = SongLab.getInstance().getSongList(getActivity(), mAlbumId);
        mSongAdapter = new SongAdapter(songList);
        mRecyclerView.setAdapter(mSongAdapter);
    }

    public void initialize(View view){
        mRecyclerView = view.findViewById(R.id.song_of_album_list_recycler_view);
        mAlbumCoverImageView = view.findViewById(R.id.album_cover_image_view);
        mArtistCoverImageView = view.findViewById(R.id.artist_icon_image_view);
        mAlbumTitleTextView = view.findViewById(R.id.album_name_text_view);
        mAlbumArtistTextView = view.findViewById(R.id.artist_of_album_text_view);
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        private TextView mSongTitleTV, mSongNumberTV, mSongDurationTv;
        private Song mSong;
        public SongHolder(@NonNull View itemView) {
            super(itemView);
            mSongDurationTv = itemView.findViewById(R.id.song_duration_text_view);
            mSongNumberTV = itemView.findViewById(R.id.song_item_number);
            mSongTitleTV = itemView.findViewById(R.id.song_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = SinglePlayMusicActivity.newIntent(getActivity(), mSong.getSongUri().toString(), getAdapterPosition(), mAlbum.getId());
                    startActivity(intent);
                }
            });
        }

        public void bind(Song song){
            mSong = song;
            mSongTitleTV.setText(song.getTitle());
            mSongNumberTV.setText("-");
            mSongDurationTv.setText(milliSecondsToTimer(song.getSongDuration()));
        }

    }

    public class SongAdapter extends RecyclerView.Adapter<SongHolder>{
        List<Song> mSongList;

        public SongAdapter(List<Song> songList) {
            mSongList = songList;
        }

        public void setSongList(List<Song> songList) {
            mSongList = songList;
        }

        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.songs_of_album_item_list, parent, false);
            return new SongHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SongHolder holder, int position) {
            holder.bind(mSongList.get(position));
        }

        @Override
        public int getItemCount() {
            return mSongList.size();
        }
    }

    private String milliSecondsToTimer(long songDuration){
        String hoursTimerString ;
        String secondsString ;
        String minutesString ;

        int hours = (int)( songDuration / (1000*60*60));
        int minutes = (int)(songDuration % (1000*60*60)) / (1000*60);
        int seconds = (int) ((songDuration % (1000*60*60)) % (1000*60) / 1000);


        hoursTimerString = hours < 10 ? "0" + hours : hours + "";
        minutesString = minutes < 10 ? "0" + minutes : minutes + "";
        secondsString = seconds < 10 ? "0" + seconds : seconds + "";

        if(songDuration < 3600*1000)
            return minutesString + ":" + secondsString;
        else
            return hoursTimerString + ":" + minutesString + ":" + secondsString;
    }

}
