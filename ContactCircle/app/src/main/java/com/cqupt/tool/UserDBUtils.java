package com.cqupt.tool;

import android.content.Context;
import android.util.Log;

import com.cqupt.bean.Circle;
import com.cqupt.bean.User;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

/**
 * Created by ls on 15-4-20.
 */
public class UserDBUtils {
    // private static final DBUtils dbUtils = new DBUtils();

    private DbUtils mUserInforDbUtils;
    private DbUtils mUserCircleDbUtils;


    public UserDBUtils(Context context) {
        DbUtils.DaoConfig config = new DbUtils.DaoConfig(context);
        config.setDbName("user.db");
        mUserInforDbUtils = DbUtils.create(config);
        config.setDbName("circle.db");
        mUserCircleDbUtils = DbUtils.create(config);

    }

    public void saveUserInforToDb(User user) {
        LogUtils.e("saveUserInforToDb()");
        try {
            mUserInforDbUtils.save(user); // 使用saveBindingId保存实体时会为实体的id赋值
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public void clearUserInfor() {
        LogUtils.e("clearUserInfor()");
        try {
            mUserInforDbUtils.deleteAll(User.class);
        } catch (DbException e) {
            e.printStackTrace();

        }
    }

    public String getUserId() {
        List<User> userList = null;
        try {
             userList = mUserInforDbUtils.findAll(User.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (userList != null)
            return userList.get(0).getId();
        else
            return null;
    }


    public List<Circle> getUserCircles() {
        List<Circle> circleList = null;
        try {
            circleList = mUserCircleDbUtils.findAll(Circle.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (circleList != null) {
            return circleList;
        } else
            return null;

    }

    public String getCircleUUID(String circle) {
        Circle mCircle = null;
        try {
              mCircle = mUserCircleDbUtils.findFirst(Selector.from(Circle.class).where("circleName","=",circle));
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (mCircle != null) {
            return mCircle.getId();
        } else
            return null;
    }

    public void clearUserCircles() {
        LogUtils.e("clearUserCircles () ");

        try {
            mUserCircleDbUtils.deleteAll(Circle.class);

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void saveUserCirclesToDb(List<Circle> circles) {
        LogUtils.e("saveUserCirclesToDb(List<Circle>circles)");

        try {
            mUserCircleDbUtils.saveAll(circles);
        } catch (DbException e) {
            e.printStackTrace();
        }


    }


    public void closeUserDbUtils() {
        if (mUserInforDbUtils != null)
            mUserInforDbUtils.close();
        if (mUserCircleDbUtils != null)
            mUserCircleDbUtils.close();
    }


    public String getUserName() {
        List<User> userList = null;
        try {
            userList = mUserInforDbUtils.findAll(User.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (userList != null)
            return userList.get(0).getName();
        else
            return "";
    }
    public String getUserAcademy() {
        List<User> userList = null;
        try {
            userList = mUserInforDbUtils.findAll(User.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (userList != null)
            return userList.get(0).getUserCollege();
        else
            return "";
    }
    public String getUserClass() {
        List<User> userList = null;
        try {
            userList = mUserInforDbUtils.findAll(User.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (userList != null)
            return userList.get(0).getUserClass();
        else
            return "";
    }
}
