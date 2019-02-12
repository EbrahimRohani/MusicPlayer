package com.example.ebrah.musicplayer.model.Song;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.ebrah.musicplayer.database.App;

import java.util.ArrayList;
import java.util.List;

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

    public List<Song> getSongList(Context context, Long albumId) {
        final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
        List<Song> songList = new ArrayList<>();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String whereClause = null;
        String [] whereArgs = null;
        if(albumId != null){
            whereClause = MediaStore.Audio.Media.ALBUM_ID + " = ? ";
            whereArgs = new String[]{albumId.toString()};
        }

        SongCursorWrapper songCursor = queryCursor(context,songUri, whereClause, whereArgs);
        Log.d(TAG, "songCursor: " + songCursor.getCount());

        try {
            if (songCursor.getCount() == 0 )
                return null;

            songCursor.moveToFirst();
            while (!songCursor.isAfterLast()) {
                Song song = songCursor.getSong(albumArtUri);
                /**Long songAlbumId = songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); */
                /**Uri albumCoverUri = getSongCover(context, songAlbumId); */
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
            Log.e(TAG, "getSongCover: ",e );
        } finally{
            albumCursor.close();
        }
        return null;
    }

    public Song getSongByUri(Context context, Long albumId, Uri songUri){
        for (Song song : getSongList(context, albumId)){
            if(song.getSongUri().equals(songUri))
                return song;
        }
        return null;
    }

    public List<Song> getSongListOfAlbum(Context context , Long albumId) {
        List<Song> songList = new ArrayList<>();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
        String whereClause = MediaStore.Audio.Media.ALBUM_ID + " = ? ";
        String[] whereArgs = new String[]{albumId.toString()};
        SongCursorWrapper songCursor = queryCursor(context, songUri, whereClause, whereArgs);
        try {
            if (songCursor.getCount() == 0)
                return null;
            songCursor.moveToFirst();
            while (!songCursor.isAfterLast()) {
                Song song = songCursor.getSong(albumArtUri);
                songList.add(song);
                songCursor.moveToNext();
            }
        }finally {
            songCursor.close();
        }
        return songList;
    }

    private SongCursorWrapper queryCursor(Context context,Uri songUri, String whereClause, String[] whereArgs){
        Cursor songCursor = context.getContentResolver().query(songUri, null, whereClause, whereArgs, null);
        return new SongCursorWrapper(songCursor);
    }

}
