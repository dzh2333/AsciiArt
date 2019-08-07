package com.mark.basemodule.application;

import android.app.Application;

import com.mark.basemodule.component.IComponentApplication;
import com.mark.basemodule.util.PermissionUtils;


public class BaseApplication extends Application implements IComponentApplication {

    private static Application instance;

    public static PermissionUtils permissionUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        permissionUtils = PermissionUtils.getInstance();
    }

    @Override
    public void init(Application application) {

    }
    public static Application getInstance() {
        return instance;
    }

}
