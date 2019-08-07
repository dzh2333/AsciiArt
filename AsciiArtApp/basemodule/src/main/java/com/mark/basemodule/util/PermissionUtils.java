package com.mark.basemodule.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.InvocationHandler;

public class PermissionUtils {

    private static PermissionUtils instance;

    private PermissionUtils(){

    }

    public static PermissionUtils getInstance(){
        if (instance == null){
            instance = new PermissionUtils();
        }
        return instance;
    }

    /**
     * 获取权限
     * @param activity
     * @param premissions
     */
    public void applyPermissions(Activity activity, String[] premissions){
        if (Build.VERSION.SDK_INT >= 23){
            boolean needApply = false;
            for (int i= 0; i < premissions.length; i++){
                int checkpremission = ContextCompat.checkSelfPermission(activity.getApplicationContext(), premissions[i]);
                if (checkpremission != PackageManager.PERMISSION_GRANTED){
                    needApply = true;
                }
            }
            if (needApply){
                ActivityCompat.requestPermissions(activity, premissions, 1);
            }
        }
    }

    public void applyPermission(Activity activity){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", PackageUtils.getPackageName(activity), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, 0);
    }
}
