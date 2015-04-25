package com.cqupt.contactcircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
 * Created by ls on 15-4-20.
 */
public class RegisterActivity extends Activity implements LoginStateListener, AdapterView.OnItemSelectedListener {

    @ViewInject(R.id.register_activity_et_student_name)
    private EditText mUserNameEditText;
    @ViewInject(R.id.register_activity_et_student_number)
    private EditText mUserNumEditText;
    @ViewInject(R.id.register_activity_et_student_password)
    private EditText mUserPassWordEditText;
    @ViewInject(R.id.register_activity_sp_college)
    private Spinner mSpinner;
    @ViewInject(R.id.register_activity_et_student_class)
    private EditText mUserClassEditText;

    private String userCollege = "通信与信息工程学院";
    private UserInforDBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewUtils.inject(this);
        initSpinner();
        dbUtils = new UserInforDBUtils(this);

    }


    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item);
        String level[] = getResources().getStringArray(R.array.college);//资源文件
        for (int i = 0; i < level.length; i++) {
            adapter.add(level[i]);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
    }


    @OnClick({R.id.register_activity_btn_goto_login, R.id.register_activity_btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_activity_btn_goto_login:
                goToOtherActivity(LoginActivity.class);
                break;
            case R.id.register_activity_btn_register:
                register();
                break;
        }


    }


    private void register() {
        String userNum = mUserNumEditText.getText().toString();
        String userPassWord = mUserPassWordEditText.getText().toString();
        String userClass = mUserClassEditText.getText().toString();
        String userName = mUserNameEditText.getText().toString();
        if (userNum == null || userPassWord == null || userClass == null || userName == null || userNum.length() != 10) {
            Toast.makeText(this, "请正确输入注册信息", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(userName, userPassWord, userNum,null, userClass, userCollege,null);
        String jsonString = JSON.toJSONString(user);

        LogUtils.e("register jsonString   " + jsonString);
        HttpHandlerUtils httpHandlerUtils = HttpHandlerUtils.getInstance();
        httpHandlerUtils.setLoginStateListener(this);
        if (jsonString != null)
            httpHandlerUtils.postInfor(App.url, "register", jsonString);

    }


    @Override
    public void loginState(String loginState) {
        LogUtils.e(" register state :  " + loginState);
        if (loginState.equals("false")) {
            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
            LogUtils.e(" register state :  " + " 注册失败");
        } else {
            goToOtherActivity(MainActivity.class);
            User user = JSON.parseObject(loginState, User.class);
            dbUtils.clearUserInfor();
            dbUtils.saveUserToDb(this, user);
        }
    }

    private void goToOtherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
        this.finish();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        userCollege = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onDestroy() {
        dbUtils.closeUserInforDbUtils();
        super.onDestroy();
    }
}
