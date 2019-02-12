package com.example.ebrah.musicplayer.controller.BottomNavLists;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ebrah.musicplayer.R;
import com.example.ebrah.musicplayer.controller.AlbumsBottomNav.SongsOfAlbumActivity;
import com.example.ebrah.musicplayer.model.Album.Album;
import com.example.ebrah.musicplayer.model.Album.AlbumLab;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumListFragment extends Fragment {
    public static final String ARGS_ARTIST_NAME = "args_artist_name";
    private List<Album> mAlbumList;
    private AlbumAdapter mAlbumAdapter;
    private RecyclerView mAlbumRecyclerView;
    private String mArtistName;

    public static AlbumListFragment newInstance(String artistName) {
        
        Bundle args = new Bundle();
        args.putSerializable(ARGS_ARTIST_NAME, artistName);
        AlbumListFragment fragment = new AlbumListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AlbumListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbumList = new ArrayList<>();
        mArtistName = (String) getArguments().getSerializable(ARGS_ARTIST_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_album_list, container, false);
        mAlbumRecyclerView = view.findViewById(R.id.album_recycler_view);
        mAlbumRecyclerView.setLayoutManager( new GridLayoutManager(getActivity(), 2));
        updateUI();
        return view;
    }


    public void updateUI(){
        mAlbumList = AlbumLab.getInstance().getAlbumList(getActivity(),mArtistName);
        mAlbumAdapter = new AlbumAdapter(mAlbumList);
        mAlbumRecyclerView.setAdapter(mAlbumAdapter);
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {
        private ImageView mAlbumCover;
        private TextView mAlbumTitle;
        private Album mAlbum;

        public AlbumHolder(@NonNull View itemView) {
            super(itemView);

            mAlbumCover = itemView.findViewById(R.id.artist_cover);
            mAlbumTitle = itemView.findViewById(R.id.artist_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = SongsOfAlbumActivity.newIntent(getActivity(), mAlbum.getId());
                    startActivity(intent);
                }
            });
        }

        public void bind(Album album){
            mAlbum = album;
            if(album.getAlbumCoverUri() != null)
                mAlbumCover.setImageURI(album.getAlbumCoverUri());
            else
                mAlbumCover.setImageResource(R.drawable.ic_all_musics);
            mAlbumTitle.setText(album.getName());
        }
    }

    public class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder>{
        private List<Album> mAlbumList;

        public AlbumAdapter(List<Album> albumList) {
            mAlbumList = albumList;
        }

        public void setAlbumList(List<Album> albumList) {
            mAlbumList = albumList;
        }

        @NonNull
        @Override
        public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.album_item_list, parent, false);
            return new AlbumHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
            holder.bind(mAlbumList.get(position));
        }

        @Override
        public int getItemCount() {
            if(mAlbumList != null)
                return mAlbumList.size();
            else
                return 0;
        }
    }

}
