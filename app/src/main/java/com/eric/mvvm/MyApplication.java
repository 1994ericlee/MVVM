package com.eric.mvvm;

import android.app.Application;

import com.eric.mvvm.utils.MacAddressUtils;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalData.macAddress = MacAddressUtils.getMacAddress();
    }
}
