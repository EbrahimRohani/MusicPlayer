package com.example.ebrah.musicplayer.model;

import android.net.Uri;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Song {

    @Id
    private Long id;
    private String title;
    private String album;
    private int songDuration;

    @Convert(converter = UriConverter.class, columnType = String.class)
    private Uri songImageUri;

    @Convert(converter = UriConverter.class, columnType = String.class)
    private Uri songUri;




    @Generated(hash = 743652352)
    public Song(Long id, String title, String album, int songDuration,
            Uri songImageUri, Uri songUri) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.songDuration = songDuration;
        this.songImageUri = songImageUri;
        this.songUri = songUri;
    }




    @Generated(hash = 87031450)
    public Song() {
    }




    public Long getId() {
        return this.id;
    }




    public void setId(Long id) {
        this.id = id;
    }




    public String getTitle() {
        return this.title;
    }




    public void setTitle(String title) {
        this.title = title;
    }




    public String getAlbum() {
        return this.album;
    }




    public void setAlbum(String album) {
        this.album = album;
    }




    public int getSongDuration() {
        return this.songDuration;
    }




    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }




    public Uri getSongImageUri() {
        return this.songImageUri;
    }




    public void setSongImageUri(Uri songImageUri) {
        this.songImageUri = songImageUri;
    }




    public Uri getSongUri() {
        return this.songUri;
    }




    public void setSongUri(Uri songUri) {
        this.songUri = songUri;
    }



    
    public static class UriConverter implements PropertyConverter<Uri, String> {

        @Override
        public Uri convertToEntityProperty(String databaseValue) {
            return Uri.parse(databaseValue);
        }

        @Override
        public String convertToDatabaseValue(Uri entityProperty) {
            return entityProperty.toString();
        }
    }
}
