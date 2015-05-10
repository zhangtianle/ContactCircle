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

import com.cqupt.app.App;
import com.cqupt.bean.Circle;
import com.cqupt.bean.User;
import com.cqupt.listener.HttpStateListener;
import com.cqupt.tool.HttpHandlerUtils;
import com.cqupt.tool.JSONUtils;
import com.cqupt.tool.UserDBUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;


import java.util.List;


/**
 * Created by ls on 15-4-20.
 */
public class RegisterActivity extends Activity implements HttpStateListener, AdapterView.OnItemSelectedListener {

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
    private UserDBUtils dbUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewUtils.inject(this);
        initSpinner();
        dbUtils = App.getAppInstance().getUserDBUtils();


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

    /**
     * 注册
     */
    private void register() {
        String userNum = mUserNumEditText.getText().toString();
        String userPassWord = mUserPassWordEditText.getText().toString();
        String userClass = mUserClassEditText.getText().toString();
        String userName = mUserNameEditText.getText().toString();
        if (userNum == null || userPassWord == null || userClass == null || userName == null || userNum.length() != 10) {
            Toast.makeText(this, "请正确输入注册信息", Toast.LENGTH_SHORT).show();
            return;
        }
//        ArrayList<Circle> circles = new ArrayList<>();
//        circles.add(new Circle("2012210625", "01", "通信学院"));
//        circles.add(new Circle("2012210625", "02", "计算机学院"));
//        circles.add(new Circle("2012210625", "03", "自动化学院"));
        User user = new User(userName, userPassWord, userNum, null, userClass, userCollege, null, null);
        String jsonString = JSONUtils.getJsonString(user);
        LogUtils.e("register jsonString   " + jsonString);
        HttpHandlerUtils httpHandlerUtils =new HttpHandlerUtils();
        httpHandlerUtils.setHttpStateListener(this);
        if (jsonString != null)
            httpHandlerUtils.postLoginOrRegisterInfor(App.downLoadURL, "register", jsonString);
        //testdb(jsonString);

    }

    private void testdb(String loginState) {

        User user = JSONUtils.parseObject(loginState, User.class);
        // List<Circle> circles = JSONUtils.parseList(loginState, "circles", Circle.class);
//        dbUtils.clearUserInfor();
//        dbUtils.saveUserInforToDb(user);
//        dbUtils.clearUserCircles();
//        dbUtils.saveUserCirclesToDb(circles);
        LogUtils.e(" user is  :  " + user);
    }

    /**
     * 注册成功回调
     */
    @Override
    public void postState(String loginState) {
        LogUtils.e(" register state :  " + loginState);
        if (loginState.equals("false")) {
            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
            LogUtils.e(" register state :  " + " 注册失败");
        } else {
            goToOtherActivity(MainActivity.class);
            User user = JSONUtils.parseObject(loginState, User.class);
            List<Circle> circles = JSONUtils.parseList(loginState, "circles", Circle.class);
            dbUtils.clearUserInfor();
            dbUtils.saveUserInforToDb(user);
            dbUtils.clearUserCircles();
            dbUtils.saveUserCirclesToDb(circles);
            if (circles != null)
                LogUtils.e(" user is  :  " + user + "   circles  is :" + circles.get(0));
        }
    }

    @Override
    public void refreshArticleState(String refreshState) {

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
        // dbUtils.closeUserDbUtils();
        super.onDestroy();
    }
}
