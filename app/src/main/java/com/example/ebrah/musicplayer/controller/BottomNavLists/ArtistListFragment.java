package com.example.ebrah.musicplayer.controller.BottomNavLists;


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
import com.example.ebrah.musicplayer.model.Artist.Artist;
import com.example.ebrah.musicplayer.model.Artist.ArtistLab;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistListFragment extends Fragment {

    private List<Artist> mArtistList;
    private ArtistAdapter mArtistAdapter;
    private RecyclerView mArtistRecyclerView;

    public static ArtistListFragment newInstance() {

        Bundle args = new Bundle();

        ArtistListFragment fragment = new ArtistListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ArtistListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArtistList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_list, container, false);
        mArtistRecyclerView = view.findViewById(R.id.artist_recycler_view);
        mArtistRecyclerView.setLayoutManager( new GridLayoutManager(getActivity(), 2));
        updateUI();
        return view;
    }

    public void updateUI(){
        mArtistList = ArtistLab.getInstance().getArtistList(getActivity());
        mArtistAdapter = new ArtistAdapter(mArtistList);
        mArtistRecyclerView.setAdapter(mArtistAdapter);
    }

    public class ArtistHolder extends RecyclerView.ViewHolder {
        private ImageView mArtistCover;
        private TextView mArtistTitle;
        private Artist mArtist;

        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
            mArtistCover = itemView.findViewById(R.id.artist_cover);
            mArtistTitle = itemView.findViewById(R.id.artist_name);
        }

        public void bind(Artist artist){
            mArtist = artist;
            mArtistTitle.setText(artist.getName());
        }
    }

    public class ArtistAdapter extends RecyclerView.Adapter<ArtistHolder> {
        private List<Artist> mArtistList;

        public ArtistAdapter(List<Artist> artistList) {
            mArtistList = artistList;
        }

        public void setArtistList(List<Artist> artistList) {
            mArtistList = artistList;
        }

        @NonNull
        @Override
        public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.artist_item_list, parent, false);
            return new ArtistHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
            holder.bind(mArtistList.get(position));
        }

        @Override
        public int getItemCount() {
            return mArtistList.size();
        }
    }

}
