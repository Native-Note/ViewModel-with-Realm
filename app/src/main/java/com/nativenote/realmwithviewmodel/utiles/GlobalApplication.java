package com.nativenote.realmwithviewmodel.utiles;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.nativenote.realmwithviewmodel.BuildConfig;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by imtiaz on 18/3/18.
 */

public class GlobalApplication extends MultiDexApplication {

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder()
                .name(BuildConfig.DBNAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
