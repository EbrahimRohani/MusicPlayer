package com.example.ebrah.musicplayer.model;

import android.net.Uri;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.converter.PropertyConverter;

@Entity
public class Album {

    @Id
    private Long id;
    private String name;
    

    @Convert(converter = UriConverter.class, columnType = String.class)
    private Uri albumCoverUri;



    @Generated(hash = 1262392339)
    public Album(Long id, String name, Uri albumCoverUri) {
        this.id = id;
        this.name = name;
        this.albumCoverUri = albumCoverUri;
    }



    @Generated(hash = 1609191978)
    public Album() {
    }



    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public String getName() {
        return this.name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public Uri getAlbumCoverUri() {
        return this.albumCoverUri;
    }



    public void setAlbumCoverUri(Uri albumCoverUri) {
        this.albumCoverUri = albumCoverUri;
    }



    public static class UriConverter implements PropertyConverter<Uri, String>{

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
