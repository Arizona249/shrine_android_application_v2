package com.example.ArinzeCommerce_v2.application;

import android.app.Application;
import android.content.Context;

public class ArneApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}