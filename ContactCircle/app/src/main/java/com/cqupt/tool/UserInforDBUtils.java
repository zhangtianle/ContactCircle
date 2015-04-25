package com.cqupt.tool;

import android.content.Context;
import android.util.Log;

import com.cqupt.bean.Circle;
import com.cqupt.bean.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

/**
 * Created by ls on 15-4-20.
 */
public class UserInforDBUtils {
    // private static final DBUtils dbUtils = new DBUtils();

    private DbUtils mUserInforDbUtils;

    public UserInforDBUtils(Context context) {
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(context);
        config.setDbName("user.db");
        mUserInforDbUtils = DbUtils.create(config);

    }

    public void saveUserToDb(Context context, User user) {
        LogUtils.e("saveUserToDb()");
        try {
            mUserInforDbUtils.save(user); // 使用saveBindingId保存实体时会为实体的id赋值
        } catch (DbException e) {
            e.printStackTrace();
        }


    }


    public void clearUserInfor() {
        LogUtils.e("clearUserInfor()" );

        try {
            mUserInforDbUtils.deleteAll(User.class);
        } catch (DbException e) {
            e.printStackTrace();

        }
    }

    public String getUserId() {

        try {
            List<User> userList = mUserInforDbUtils.findAll(User.class);
            LogUtils.e("userList.size" + userList.size());
            return userList.get(0).getId();
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Circle> getUserCircles() {
        List<User> userList = null;
        try {
            userList = mUserInforDbUtils.findAll(User.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (userList != null){
            LogUtils.e("userList.size" + userList.size());
            return userList.get(0).getCircles();
        }
        else
            return null;
    }


    public void closeUserInforDbUtils() {
        mUserInforDbUtils.close();
    }

}
