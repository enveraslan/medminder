package com.aae.medminder;

import android.app.Application;
import android.content.Context;

import com.aae.medminder.database.DbOpenHelper;
import com.aae.medminder.models.DaoMaster;
import com.aae.medminder.models.DaoSession;

public class MedminderApp extends Application {
    private static DaoSession daoSession;
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        daoSession = new DaoMaster(new DbOpenHelper(this, "medminder.db").getWritableDb()).newSession();
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static void UpdateSession(){
        daoSession = new DaoMaster(new DbOpenHelper(appContext, "medminder.db").getWritableDb()).newSession();
    }

}
