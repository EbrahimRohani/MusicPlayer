package com.example.ebrah.musicplayer.model;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ArtistLab {
    public static final String TAG = "nooz";
    private static ArtistLab instance;


    private ArtistLab(){

    }

    public static ArtistLab getInstance(){
        if(instance == null)
            instance = new ArtistLab();
        return instance;
    }

    public List<Artist> getArtistList(Context context){
        List<Artist> artists = new ArrayList<>();
        Uri artistUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor artistCursor = context.getContentResolver().query(artistUri, null, null, null,null,null);

        try {
            if (artistCursor.getCount() == 0)
                return null;
            artistCursor.moveToFirst();
            while (!artistCursor.isAfterLast()) {
                Long artistId = artistCursor.getLong(artistCursor.getColumnIndex(MediaStore.Audio.Artists._ID));
                String title = artistCursor.getString(artistCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST));

                Artist artist = new Artist(artistId, title);
                artists.add(artist);
                Log.e(TAG, "albumList: " + artists.size());
                artistCursor.moveToNext();
            }
        }catch (Exception e){
            Log.e(TAG, "albumList: ", e);
        } finally{
            artistCursor.close();
        }
        return artists;
    }
}
