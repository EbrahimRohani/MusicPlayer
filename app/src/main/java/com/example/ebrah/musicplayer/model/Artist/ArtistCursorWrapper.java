package com.example.ebrah.musicplayer.model.Artist;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.provider.MediaStore;

public class ArtistCursorWrapper extends CursorWrapper {

    public ArtistCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Artist getArtist(){
        Long id = getLong(getColumnIndex(MediaStore.Audio.Artists._ID));
        String name = getString(getColumnIndex(MediaStore.Audio.Artists.ARTIST));

        return new Artist(id, name);
    }
}
