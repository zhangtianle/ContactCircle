package com.cqupt.app;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by ls on 15-4-19.
 */
public class App extends Application {
    public static final int GET_PHOTO_REQUEST_CODE = 1;
    public static final int GET_ATTACHMENT_REQUEST_CODE = 2;
    public static final String url="http://222.182.106.128:8080/Lianluoquan/Register";

    private static App mApp;



    @Override
    public void onCreate() {
        mApp = this;
        super.onCreate();

    }


    public static App getAppInstance() {
        return mApp;
    }


}
