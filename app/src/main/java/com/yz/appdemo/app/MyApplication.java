package com.yz.appdemo.app;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import com.yz.appdemo.common.Constant;
import com.yz.appdemo.util.log.CrashHandler;
import com.yz.appdemo.util.log.Logger;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import es.dmoral.toasty.Toasty;

public class MyApplication extends Application {
    private static MyApplication mApplication;
    public static final Executor EXECUTOR = Executors.newCachedThreadPool();

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initCrash();
        init();
    }

    private void init() {
        Toasty.Config.getInstance()
                .allowQueue(false)
                .apply();
        Constant.FILE_ROOT_PATH = getExternalFilesDir(null).getAbsolutePath();
        Constant.FILE_IMAGE_PATH = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();

    }

    private void initCrash() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            CrashHandler.init(getExternalCacheDir() +  "/log", new CrashHandler.OnCrashListener() {
                @Override
                public void onCrash(String crashInfo, Throwable e) {
                    Logger.e(crashInfo);
                }
            });
        }
    }

    public static Application getApplication() {
        return mApplication;
    }
}
