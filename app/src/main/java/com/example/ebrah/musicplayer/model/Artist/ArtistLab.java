package com.example.ebrah.musicplayer.model.Artist;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.ebrah.musicplayer.model.Album.Album;

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
        ArtistCursorWrapper artistCursorWrapper = queryCursor(context, artistUri, null, null);

        try {
            if (artistCursorWrapper.getCount() == 0)
                return null;
            artistCursorWrapper.moveToFirst();
            while (!artistCursorWrapper.isAfterLast()) {
                Artist artist = artistCursorWrapper.getArtist();
                artists.add(artist);
                Log.e(TAG, "getAlbumList: " + artists.size());
                artistCursorWrapper.moveToNext();
            }
        }catch (Exception e){
            Log.e(TAG, "getAlbumList: ", e);
        } finally{
            artistCursorWrapper.close();
        }
        return artists;
    }

    private ArtistCursorWrapper queryCursor (Context context,Uri artistUri, String whereClause, String[] whereArgs){
        Cursor artistCursor = context.getContentResolver().query(artistUri, null, whereClause, whereArgs, null);
        return new ArtistCursorWrapper(artistCursor);
    }
}
