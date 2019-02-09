package com.example.ebrah.musicplayer.controller.Mains;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ebrah.musicplayer.R;
import com.example.ebrah.musicplayer.controller.BottomNavLists.AlbumListFragment;
import com.example.ebrah.musicplayer.controller.BottomNavLists.ArtistListFragment;
import com.example.ebrah.musicplayer.controller.BottomNavLists.SongListFragment;
import com.example.ebrah.musicplayer.model.MainPlayer.MainPlayer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAudioPermissions();
        mBottomNavigationView = findViewById(R.id.bottom_nav);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.tab_songs:
                        SongListFragment songListFragment = SongListFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, songListFragment).commit();
                        break;

                    case R.id.tab_albums:
                        AlbumListFragment albumListFragment = AlbumListFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, albumListFragment).commit();
                        break;

                    case R.id.tab_artists:
                        ArtistListFragment artistListFragment = ArtistListFragment.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, artistListFragment).commit();
                }
                return true;
            }
        });
    }

    private void requestAudioPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //When permission is not granted by user, show them message why this permission is needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();

                //Give user option to still opt-in the permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            } else {
                // Show user dialog to grant permission to record audio
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                // do your stuff
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainPlayer.getInstance().release();
    }
}


