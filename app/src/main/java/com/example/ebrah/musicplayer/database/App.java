package com.example.ebrah.musicplayer.database;

import android.app.Application;

import com.example.ebrah.musicplayer.model.DaoMaster;
import com.example.ebrah.musicplayer.model.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {
    private static App mApp;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        MyDevOpenHelper myDevOpenHelper = new MyDevOpenHelper(this,"Song.db");
        Database database = myDevOpenHelper.getWritableDb();
        mDaoSession = new DaoMaster(database).newSession();
        mApp = this;
    }

    public static App getApp() {
        return mApp;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
