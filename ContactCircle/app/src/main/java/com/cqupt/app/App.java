package com.cqupt.app;

import android.app.Application;

import com.cqupt.tool.ArticleDBUtils;
import com.cqupt.tool.UserDBUtils;

/**
 * Created by ls on 15-4-19.
 */
public class App extends Application {
    public static final int GET_PHOTO_REQUEST_CODE = 1;
    public static final int GET_ATTACHMENT_REQUEST_CODE = 2;
    public static final String downLoadURL = "http://113.251.167.81/Lianluoquan/Register";
    public static final String upLoadURL = "http://113.251.167.81/Lianluoquan/Login";
    private UserDBUtils userDBUtils;
    private ArticleDBUtils articleDBUtils;
    private static App mApp;

    @Override
    public void onCreate() {
        mApp = this;
        userDBUtils = new UserDBUtils(this);
        articleDBUtils = new ArticleDBUtils(this);
        super.onCreate();
    }


    public static App getAppInstance() {
        return mApp;
    }

    @Override
    public void onLowMemory() {
        if (userDBUtils != null)
            userDBUtils.closeUserDbUtils();
        if (articleDBUtils != null)
            articleDBUtils.closeArticleInforDbUtils();
        super.onLowMemory();
    }


    public UserDBUtils getUserDBUtils() {
        return userDBUtils;
    }

    public ArticleDBUtils getArticleDBUtils() {
        return articleDBUtils;
    }

}
