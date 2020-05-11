package com.yz.appdemo.app;

import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyApplication extends Application {
    private static MyApplication mApplication;
    public static final Executor EXECUTOR = Executors.newCachedThreadPool();

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Application getApplication() {
        return mApplication;
    }
}
