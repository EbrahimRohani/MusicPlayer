package com.example.ebrah.musicplayer.controller;


import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebrah.musicplayer.R;
import com.example.ebrah.musicplayer.model.Song;
import com.example.ebrah.musicplayer.model.SongLab;

import java.util.ArrayList;
import java.util.List;


public class SongListFragment extends Fragment {

    private RecyclerView mSongsRecyclerView;
    private SongAdapter mSongAdapter;
    private List<Song> mSongList;

    public static SongListFragment newInstance() {

        Bundle args = new Bundle();

        SongListFragment fragment = new SongListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSongList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_song, container, false);
        mSongsRecyclerView = view.findViewById(R.id.songs_recycler_view);

        mSongsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    public void updateUI(){
        mSongList = SongLab.getInstance().getSongList(getActivity());
        Log.i("hello", "updateUI: " + mSongList.size());
        mSongAdapter = new SongAdapter(mSongList);
        mSongsRecyclerView.setAdapter(mSongAdapter);
    }

    public class SongHolder extends RecyclerView.ViewHolder {

        private TextView mSongTitleTextView, mAlbumTitleTextView;
        private ImageView mSongIconImageView;
        private Song mSong;
        public SongHolder(@NonNull View itemView) {
            super(itemView);

            mSongIconImageView = itemView.findViewById(R.id.song_icon);
            mAlbumTitleTextView = itemView.findViewById(R.id.album_of_song_text_view);
            mSongTitleTextView = itemView.findViewById(R.id.song_text_view);
        }

        public void bind(Song song){
            mSong = song;
            mSongTitleTextView.setText(song.getTitle());
            mAlbumTitleTextView.setText(song.getAlbum());
            if (song.getSongUri() == null)
                mSongIconImageView.setImageResource(R.drawable.ic_all_musics);
            else
                mSongIconImageView.setImageURI(song.getSongUri());


        }
    }

    public class SongAdapter extends RecyclerView.Adapter<SongHolder>{
        private List<Song> mSongList;

        public SongAdapter(List<Song> songList) {
            mSongList = songList;
        }

        public void setSongList(List<Song> songList) {
            mSongList = songList;
        }

        @NonNull
        @Override
        public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.song_item_list, parent, false);
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


}

