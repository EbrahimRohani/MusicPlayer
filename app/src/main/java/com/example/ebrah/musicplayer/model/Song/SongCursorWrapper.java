package com.example.ebrah.musicplayer.model.Song;

import android.content.ContentUris;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.ebrah.musicplayer.model.Song.Song;

public class SongCursorWrapper extends CursorWrapper {

    public SongCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Song getSong(Uri albumArtUri){
        Long songId = getLong(getColumnIndex(MediaStore.Audio.Media._ID));
        String songTitle = getString(getColumnIndex(MediaStore.Audio.Media.TITLE));
        String artistTitle = getString(getColumnIndex(MediaStore.Audio.Media.ARTIST));
        int songDuration  = getInt(getColumnIndex(MediaStore.Audio.Media.DURATION));
        Uri songUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
        Uri albumCoverUri = ContentUris.withAppendedId(albumArtUri, getLong(getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));

        return new Song(songId, songTitle, artistTitle, songDuration, albumCoverUri, songUri);
    }
}
