package com.cqupt.contactcircle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cqupt.app.App;
import com.cqupt.tool.HttpHandlerUtils;
import com.cqupt.tool.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ls on 15-4-18.
 */
public class SendMessageActivity extends Activity {
    @ViewInject(R.id.send_message_activity_preview_iv_camera)
    private ImageView mPreviewPhoto;
    @ViewInject(R.id.send_message_activity_preview_ll)
    private LinearLayout mPreviewGroup;
    private Uri mBitmapUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        ViewUtils.inject(this);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @OnClick({R.id.send_message_activity_tool_iv_attachment, R.id.send_message_activity_tool_iv_camera,
            R.id.send_message_activity_preview_iv_camera})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_message_activity_tool_iv_attachment:
                startFileManager();
                //HttpHandlerUtils.downLoad("http://www.people.com.cn/mediafile/pic/GQ/20150414/64/16486532101230056244.jpg");
                //  DBUtils.saveUserToDb(this);
                // HttpHandlerUtils.downLoad("http://openbox.mobilem.360.cn/index/d/sid/2041682");
                //   System.out.println("1111HttpUtils");
                // HttpHandlerUtils.postInfor("http://113.251.216.145:8080/Lianluoquan/Login");
                break;
            case R.id.send_message_activity_tool_iv_camera:
                // System.out.println("send_message_activity_tool_iv_camera");
                startCamera();
                break;
            case R.id.send_message_activity_preview_iv_camera:
                cancelPhoto();
                break;
        }

    }

    /**
     * 取消图片附件，暂时处理隐藏
     */
    private void cancelPhoto() {
        mPreviewPhoto.setVisibility(View.INVISIBLE);
    }

    public void startFileManager() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
                    App.GET_ATTACHMENT_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT)
                    .show();
        }

    }


    /**
     * 根据返回选择的文件，来进行上传操作 *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case App.GET_ATTACHMENT_REQUEST_CODE:
                    getAttachment(data);
                    break;
                case App.GET_PHOTO_REQUEST_CODE:
                    getPhoto();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void getPhoto() {
        mPreviewGroup.setVisibility(View.VISIBLE);
        String imageName = System.currentTimeMillis() + ".jpg";
        if (mBitmapUri != null) {
            Bitmap image;
            try {
                image = Utils.getBitmap(mBitmapUri, this, 500);
                if (image != null) {
                    mPreviewPhoto.setImageBitmap(image);
                    File file = Utils.saveBitmapToFile(imageName, image);
                    //HttpHandlerUtils.uploadFile(file.getAbsolutePath(),"http://113.251.216.145:8080/Lianluoquan/Login");
                    //HttpHandlerUtils.upLaod("http://113.250.156.102:8080/Lianluoquan/Login", file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void startCamera() {
        File file = Utils.getAlbumStorageDir("test " + System.currentTimeMillis() + ".jpg");
        mBitmapUri = Uri.fromFile(file);
        Intent localIntent1 = new Intent();
        localIntent1.setAction("android.media.action.IMAGE_CAPTURE");
        localIntent1.putExtra(MediaStore.EXTRA_OUTPUT, mBitmapUri); //指定图片输出地址
        startActivityForResult(localIntent1, App.GET_PHOTO_REQUEST_CODE);
    }

    public void getAttachment(Intent data) {
        Uri mUri = data.getData();
        File file = new File(mUri.getPath());
        //HttpHandlerUtils.upLaod("http://113.250.156.102:8080/Lianluoquan/Login", file);
    }
}
