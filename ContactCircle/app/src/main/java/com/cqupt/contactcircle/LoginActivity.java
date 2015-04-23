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
import com.cqupt.tool.HttpHandlerUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by ls on 15-4-20.
 */
public class LoginActivity extends Activity implements LoginStateListener {


    @ViewInject(R.id.login_activity_et_student_name)
    private EditText mUserName;
    @ViewInject(R.id.login_activity_et_student_number)
    private EditText mUserNum;
    @ViewInject(R.id.login_activity_et_student_password)
    private EditText mUserPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewUtils.inject(this);


    }


    @OnClick({R.id.login_activity_btn_login, R.id.login_activity_btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_activity_btn_login:

               // login();
                break;

            case R.id.login_activity_btn_register:
                login();
                break;
        }


    }

    private void login() {
        String userNum = mUserNum.getText().toString();
        String userPassWord = mUserPassWord.getText().toString();
        System.out.println("userNum : "+userNum + " userPassWord :  " +userPassWord );
        if (userNum == null || userPassWord == null || userNum.length() != 10) {
            Toast.makeText(this, "请正确输入登陆信息", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(null, userPassWord, userNum, null, null, null);
        String jsonString = JSON.toJSONString(user);
        HttpHandlerUtils httpHandlerUtils =HttpHandlerUtils.getInstance();
        httpHandlerUtils.setLoginStateListener(this);
        if (jsonString != null)
            httpHandlerUtils.postInfor(App.url, "register", jsonString);

    }

    @Override
    public void loginState(String loginState) {
        System.out.println("login state : " + loginState);
        if (loginState.equals("false")) {
            Toast.makeText(this, "登陆失败！", Toast.LENGTH_SHORT).show();
        } else
            goToMainActivity();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
