package com.example.ebrah.musicplayer.model.Album;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.ebrah.musicplayer.model.Artist.ArtistCursorWrapper;
import com.example.ebrah.musicplayer.model.Song.Song;

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

    public List<Album> getAlbumList(Context context, String artistName){
        List<Album> albums = new ArrayList<>();
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        String whereClause = null;
        String[]whereArgs = null;

        if(artistName != null){
            whereClause = MediaStore.Audio.Albums.ARTIST + " = ? ";
            whereArgs = new String[]{artistName};
        }

        AlbumCursorWrapper albumCursorWrapper = queryCursor(context, albumUri, whereClause, whereArgs);

        try {
            if (albumCursorWrapper.getCount() == 0)
                return null;
            albumCursorWrapper.moveToFirst();
            while (!albumCursorWrapper.isAfterLast()) {
                Album album = albumCursorWrapper.getAlbum();
                albums.add(album);
                Log.e(TAG, "getAlbumList: " + albums.size());
                albumCursorWrapper.moveToNext();
            }
        }catch (Exception e){
            Log.e(TAG, "getAlbumList: ", e);
        } finally{
            albumCursorWrapper.close();
        }
        return albums;
    }

    public Album getAlbumById(Context context, Long albumId, String artistName) {
        for (Album album : getAlbumList(context, artistName)) {
            if (album.getId().equals(albumId))
                return album;
        }
        return null;
    }

    private AlbumCursorWrapper queryCursor(Context context,Uri albumUri, String whereClause, String[] whereArgs){
        Cursor songCursor = context.getContentResolver().query(albumUri, null, whereClause, whereArgs, null);
        return new AlbumCursorWrapper(songCursor);
    }
}
