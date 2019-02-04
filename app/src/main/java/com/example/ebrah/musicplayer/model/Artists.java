package com.example.ebrah.musicplayer.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Artists {
    @Id
    private Long id;
    private String name;
    @Generated(hash = 189774094)
    public Artists(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1190535900)
    public Artists() {
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
