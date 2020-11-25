package com.yz.appdemo.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.yz.appdemo.util.log.Logger;

public class PermissionUtil {
    public static int REQUEST_PERMISSION_CODE = 10000;

    public static void requestPermissions(Activity activity, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (int i = 0; i < permissions.length; i++) {
                if (ActivityCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, permissions, PermissionUtil.REQUEST_PERMISSION_CODE);
                    break;
                }
            }
        }
    }


    /**
     * 检查权限，没有则申请
     */
    public static boolean checkPermission(Activity context, String permission) {
        boolean granted = ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        if (!granted) {
            requestPermissions(context, permission);
        }
        return granted;
    }

    //结果回调
    public static void onRequestResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionUtil.REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Logger.e("request" + permissions[i] + " failed");
                }
            }
        }
    }
}
