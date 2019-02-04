package com.example.ebrah.musicplayer.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Album {

    @Id
    private Long id;
    private String name;
    @Generated(hash = 1836452385)
    public Album(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
