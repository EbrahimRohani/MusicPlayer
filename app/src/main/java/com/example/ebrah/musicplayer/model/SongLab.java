package com.example.ebrah.musicplayer.model;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.renderscript.ScriptC;
import android.util.Log;

import com.example.ebrah.musicplayer.database.App;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SongLab {
    public static final String TAG = "noob";
    private static SongLab instance;
    private SongDao mSongDao;
    private DaoSession mDaoSession;

    private SongLab(){
        mDaoSession = (App.getApp()).getDaoSession();
        mSongDao = mDaoSession.getSongDao();
    }

    public static SongLab getInstance(){
        if(instance == null)
            instance = new SongLab();
        return instance;
    }

    public List<Song> getSongList(Context context) {
        //final String INIT_URI = "file:///";
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
                //String musicPath = songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                //Uri musicUri = Uri.parse(INIT_URI + musicPath);
                int songDuration  = songCursor.getInt(songCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                Uri musicUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songId);
                Uri albumCoverUri = ContentUris.withAppendedId(albumArtUri, songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                Long songAlbumId = songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                /**Uri albumCoverUri = getSongCover(context, songAlbumId); */
                Song song = new Song(songId, songTitle, artistTitle, songDuration, albumCoverUri, musicUri);

                songList.add(song);
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

    public Uri getSongCover(Context context, Long albumId){
        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String whereClause = MediaStore.Audio.Albums._ID + " = ? ";
        String[] whereArgs = new String[]{albumId.toString()};
        Cursor albumCursor = context.getContentResolver().query(albumUri, new String[]{MediaStore.Audio.Albums._ID}, whereClause,whereArgs,null);
        try {
            if (albumCursor.getCount() == 0)
                return null;
            albumCursor.moveToFirst();
            while(!albumCursor.isAfterLast()) {
                return Uri.parse(albumCursor.getString(albumCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
            }

        }catch (Exception e){
            Log.e("soop", "getSongCover: ",e );
        } finally{
            albumCursor.close();
        }
        return null;
    }

    public Song getSongByUri(Context context, Uri songUri){
        for (Song song : getSongList(context)){
            if(song.getSongUri().equals(songUri))
                return song;
        }
        return null;
    }


}
