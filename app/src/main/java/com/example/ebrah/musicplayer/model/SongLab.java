package com.example.ebrah.musicplayer.model;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.ebrah.musicplayer.database.App;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SongLab {
    public static final String TAG = "noob";
    private static SongLab instance;
    private SongDao mSongDao;

    private SongLab(){
    }

    public static SongLab getInstance(){
        if(instance == null)
            instance = new SongLab();
        return instance;
    }

    public List<Song> getSongList(Context context) {
        final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
        List<Song> songList = new ArrayList<>();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = context.getContentResolver().query(songUri, null, null, null, null);
        Log.d(TAG, "songCursor: " + songCursor.getCount());

        try {
            if (songCursor.getCount() == 0 )
                return null;

            songCursor.moveToFirst();
            while (!songCursor.isAfterLast()) {
                Long songId = songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media._ID));
                String songTitle = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artistTitle = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                Uri albumCoverUri = ContentUris.withAppendedId(albumArtUri, songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));

                Song song = new Song(songId, songTitle,artistTitle,albumCoverUri);

                songList.add(song);
                Log.d(TAG, "Song added " + songList.size());
                songCursor.moveToNext();
            }
        } catch (Exception e){
            Log.d(TAG, "getSongList: ", e);
        } finally{
            songCursor.close();
        }
        Log.d(TAG, "getSongList: " +songList.size());
        return songList;
    }


}
