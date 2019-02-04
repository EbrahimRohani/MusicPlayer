package com.example.ebrah.musicplayer.model;

import android.net.Uri;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.converter.PropertyConverter;

@Entity
public class Artist {
    @Id
    private Long id;
    private String name;
    @Generated(hash = 401107274)
    public Artist(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 19829037)
    public Artist() {
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

    

}
