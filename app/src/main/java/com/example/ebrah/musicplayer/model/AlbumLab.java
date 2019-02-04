package com.example.ebrah.musicplayer.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class AlbumLab {
    public static final String TAG = "nooz";
    private static AlbumLab instance;


    private AlbumLab(){

    }

    public static AlbumLab getInstance(){
        if(instance == null)
            instance = new AlbumLab();
        return instance;
    }

    public List<Album> albumList(Context context){
        List<Album> albums = new ArrayList<>();
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor albumCursor = context.getContentResolver().query(albumUri, null, null, null,null,null);

        try {
            if (albumCursor.getCount() == 0)
                return null;
            albumCursor.moveToFirst();
            while (!albumCursor.isAfterLast()) {
                Long albumId = albumCursor.getLong(albumCursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String title = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM));
                String albumCoverString = albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                Uri albumCoverUri;
                if(albumCoverString != null)
                    albumCoverUri = Uri.parse(albumCoverString);
                else
                    albumCoverUri = null;

                Album album = new Album(albumId, title, albumCoverUri);
                albums.add(album);
                Log.e(TAG, "albumList: " + albums.size());
                albumCursor.moveToNext();
            }
        }catch (Exception e){
            Log.e(TAG, "albumList: ", e);
        } finally{
            albumCursor.close();
        }
        return albums;
    }
}
