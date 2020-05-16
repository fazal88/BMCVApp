package com.androidvoyage.bmc;

import android.app.Application;
import android.content.Context;

/**
 * Created by Adveti Creatives on 25-07-2017.
 */

public class BCApplication extends Application {

    private static volatile Context context;

    public static Context getAppContext() {
        return BCApplication.context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        /*MultiDex.install(this);*/
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

}
