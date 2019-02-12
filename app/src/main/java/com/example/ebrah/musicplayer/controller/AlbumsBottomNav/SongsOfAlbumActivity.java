package com.example.ebrah.musicplayer.controller.AlbumsBottomNav;

import android.content.Context;
import android.content.Intent;

import com.example.ebrah.musicplayer.controller.Mains.SingleFragmentActivity;

import androidx.fragment.app.Fragment;

public class SongsOfAlbumActivity extends SingleFragmentActivity {

    public static final String EXTRA_ALBUM_ID = "com.example.ebrah.musicplayer.controller.extra_album_id";

    public static Intent newIntent(Context context, Long albumId){
        Intent intent = new Intent(context, SongsOfAlbumActivity.class);
        intent.putExtra(EXTRA_ALBUM_ID, albumId);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        Long albumId = getIntent().getLongExtra(EXTRA_ALBUM_ID, 0);
        return SongsOfAlbumFragment.newInstance(albumId);
    }

}
