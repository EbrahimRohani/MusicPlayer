package com.example.ebrah.musicplayer.model.Album;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.ebrah.musicplayer.model.Album.Album;

public class AlbumCursorWrapper extends CursorWrapper {
    public AlbumCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Album getAlbum(){

        Long albumId = getLong(getColumnIndex(MediaStore.Audio.Albums._ID));
        String title = getString(getColumnIndex(MediaStore.Audio.Albums.ALBUM));
        String albumCoverString = getString(getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
        String artistName = getString(getColumnIndex(MediaStore.Audio.Albums.ARTIST));
        Uri albumCoverUri;
        if(albumCoverString != null)
            albumCoverUri = Uri.parse(albumCoverString);
        else
            albumCoverUri = null;

        return new Album(albumId, title, artistName, albumCoverUri);
    }
}
