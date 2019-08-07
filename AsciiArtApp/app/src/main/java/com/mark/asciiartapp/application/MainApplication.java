package com.mark.asciiartapp.application;

import android.app.Application;

import com.mark.basemodule.application.BaseApplication;
import com.mark.basemodule.component.IComponentApplication;
import com.mark.basemodule.util.PermissionUtils;

public class MainApplication extends BaseApplication {

    private static final String[] MODULESLIST =
            {""};


    @Override
    public void onCreate() {
        super.onCreate();


        modulesApplicationInit();
    }

    private void modulesApplicationInit(){
        for (String moduleImpl : MODULESLIST){
            try {
                Class<?> clazz = Class.forName(moduleImpl);
                Object obj = clazz.newInstance();
                if (obj instanceof IComponentApplication){
                    ((IComponentApplication) obj).init(BaseApplication.getInstance());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
