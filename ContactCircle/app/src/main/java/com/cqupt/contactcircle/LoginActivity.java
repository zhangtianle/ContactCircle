package com.cqupt.contactcircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cqupt.app.App;
import com.cqupt.bean.Circle;
import com.cqupt.bean.User;
import com.cqupt.listener.HttpStateListener;
import com.cqupt.tool.JSONUtils;
import com.cqupt.tool.UserDBUtils;
import com.cqupt.tool.HttpHandlerUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

/**
 * Created by ls on 15-4-23.
 */
public class LoginActivity extends Activity implements HttpStateListener {
    @ViewInject(R.id.login_activity_et_student_number)
    private EditText mUserNumEditText;
    @ViewInject(R.id.login_activity_et_student_password)
    private EditText mPassWordEditText;
    private UserDBUtils dbUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        LogUtils.e(" login  activity ");
        dbUtils = App.getAppInstance().getUserDBUtils();
        if (dbUtils.getUserId() != null) {
            LogUtils.e(" login  activity   dbUtils.getUserId() != null ");
            goToOtherActivity(MainActivity.class);
        }
    }

    @OnClick({R.id.login_activity_btn_login, R.id.login_activity_btn_goto_register})
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.login_activity_btn_goto_register:

                goToOtherActivity(RegisterActivity.class);
                break;
            case R.id.login_activity_btn_login:
                login();

                break;
        }

    }

    private void login() {
        String userNum = mUserNumEditText.getText().toString();
        String userPassWord = mPassWordEditText.getText().toString();
        if (userNum == null || userNum.length() != 10 || userPassWord.equals("")) {
            Toast.makeText(this, "请正确输入登陆信息", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(null, userPassWord, userNum, null, null, null, null,null);
        String jsonString = JSONUtils.getJsonString(user);
        LogUtils.e("login jsonString   " + jsonString);
        HttpHandlerUtils httpHandlerUtils = HttpHandlerUtils.getInstance();
        httpHandlerUtils.setHttpStateListener(this);
        if (jsonString != null)
            httpHandlerUtils.postLoginOrRegisterInfor(App.downLoadURL, "login", jsonString);
    }

    private void goToOtherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        this.finish();
    }


    @Override
    public void loginOrRegisterState(String loginState) {
        LogUtils.e(" login  state :  " + loginState);

        if (loginState.equals("false")) {
            Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
        } else {
            goToOtherActivity(MainActivity.class);
            User user = JSONUtils.parseObject(loginState, User.class);
            List<Circle> circles = JSONUtils.parseList(loginState, "circles", Circle.class);
            dbUtils.clearUserInfor();
            dbUtils.saveUserInforToDb(user);
            dbUtils.clearUserCircles();
            dbUtils.saveUserCirclesToDb(circles);
            LogUtils.e(" user is  :  " + user);
        }
    }

    @Override
    public void refreshArticleState(String refreshState) {


    }


    @Override
    protected void onDestroy() {
        //dbUtils.closeUserDbUtils();
        super.onDestroy();
    }
}
