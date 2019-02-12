package com.example.ebrah.musicplayer.controller.ArtistBottomNav;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.ebrah.musicplayer.R;
import com.example.ebrah.musicplayer.controller.BottomNavLists.AlbumListFragment;
import com.example.ebrah.musicplayer.controller.Mains.SingleFragmentActivity;

public class AlbumsOfArtist extends SingleFragmentActivity {

    public static final String EXTRA_ARTIST_NAME = "com.example.ebrah.musicplayer.controller.ArtistBottomNav.extra_artist_name";

    public static Intent newIntent(Context context, String artistName){
        Intent intent = new Intent(context, AlbumsOfArtist.class);
        intent.putExtra(EXTRA_ARTIST_NAME, artistName);
        return intent;
    }
    @Override
    public Fragment createFragment() {
        String artistName = (String) getIntent().getSerializableExtra(EXTRA_ARTIST_NAME);
        return AlbumListFragment.newInstance(artistName);
    }
}

