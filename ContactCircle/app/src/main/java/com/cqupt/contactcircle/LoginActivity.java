package com.cqupt.contactcircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cqupt.app.App;
import com.cqupt.bean.User;
import com.cqupt.listener.LoginStateListener;
import com.cqupt.tool.UserInforDBUtils;
import com.cqupt.tool.HttpHandlerUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by ls on 15-4-23.
 */
public class LoginActivity extends Activity implements LoginStateListener {
    @ViewInject(R.id.login_activity_et_student_number)
    private EditText mUserNumEditText;
    @ViewInject(R.id.login_activity_et_student_password)
    private EditText mPassWordEditText;
    private UserInforDBUtils dbUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);
        dbUtils = new UserInforDBUtils(this);
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
        User user = new User(null, userPassWord, userNum, null, null, null, null);
        String jsonString = JSON.toJSONString(user);
        LogUtils.e("login jsonString   " + jsonString);
        HttpHandlerUtils httpHandlerUtils = HttpHandlerUtils.getInstance();
        httpHandlerUtils.setLoginStateListener(this);
        if (jsonString != null)
            httpHandlerUtils.postInfor(App.url, "login", jsonString);

    }

    private void goToOtherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        this.finish();

    }


    @Override
    public void loginState(String loginState) {
        LogUtils.e(" login  state :  " + loginState);

        if (loginState.equals("false")) {
            Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show();
        } else {
            goToOtherActivity(MainActivity.class);
            User user = JSON.parseObject(loginState, User.class);
            dbUtils.clearUserInfor();
            dbUtils.saveUserToDb(this, user);
            String id = dbUtils.getUserId();
            LogUtils.e("login id is :" + id + " login circles is :" + dbUtils.getUserCircles().size());
        }
    }


    @Override
    protected void onDestroy() {
        dbUtils.closeUserInforDbUtils();
        super.onDestroy();
    }
}
