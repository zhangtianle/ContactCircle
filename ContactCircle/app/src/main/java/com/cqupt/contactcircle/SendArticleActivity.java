package com.cqupt.contactcircle;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cqupt.app.App;
import com.cqupt.bean.SendArticle;
import com.cqupt.listener.HttpStateListener;
import com.cqupt.tool.HttpHandlerUtils;
import com.cqupt.tool.JSONUtils;
import com.cqupt.tool.UserDBUtils;
import com.cqupt.tool.Utils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ls on 15-4-18.
 */
public class SendArticleActivity extends Activity implements HttpStateListener {
    @ViewInject(R.id.send_article_activity_preview_iv_camera)
    private ImageView mPreviewPhoto;
    @ViewInject(R.id.send_message_activity_preview_iv_attachment)
    private ImageView mPreviewAttachment;
    @ViewInject(R.id.send_article_activity_preview_ll)
    private LinearLayout mPreviewGroup;
    @ViewInject(R.id.send_article_activity_et_content)
    private EditText mArticleContent;
    @ViewInject(R.id.send_article_activity_et_title)
    private EditText mArticleTitle;

    @ViewInject(R.id.send_article_activity_tx_user_name)
    private TextView mUserName;
    @ViewInject(R.id.send_article_activity_tx_circle)
    private TextView mUserCircle;


    private String mArticleCircle = "通信学院";//先这样默认的
    private Uri mBitmapUri;
    private List<File> mAttachmentFiles;
    private File mPhotoAttachment;
    private File mFileAttachment;
    private UserDBUtils mUserDBUtils;
    private HttpHandlerUtils mHttpHandlerUtils;
    private MaterialDialog dialog;

    public SendArticleActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_article);
        ViewUtils.inject(this);
        mUserDBUtils = App.getAppInstance().getUserDBUtils();
        mHttpHandlerUtils = HttpHandlerUtils.getInstance();
        mHttpHandlerUtils.setHttpStateListener(this);
        mAttachmentFiles = new ArrayList<>();
        initInforView();
        initDialog();
    }

    private void initDialog() {
        dialog = new MaterialDialog.Builder(this)
                .title("正在发送")
                .content("请稍候...")
                .progress(true, 0)
                .build();
    }

    private void initInforView() {
        mUserName.setText(mUserDBUtils.getUserName());
        mUserCircle.setText("所有圈子");
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


    @OnClick({R.id.send_article_activity_tool_iv_attachment, R.id.send_article_activity_tool_iv_camera,
            R.id.send_article_activity_preview_iv_camera, R.id.send_article_activity_iv_send, R.id.send_message_activity_preview_iv_attachment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_article_activity_tool_iv_attachment://附件按钮
                startFileManager();
                //HttpHandlerUtils.downLoad("http://www.people.com.cn/mediafile/pic/GQ/20150414/64/16486532101230056244.jpg");
                //  DBUtils.saveUserInforToDb(this);
                // HttpHandlerUtils.downLoad("http://openbox.mobilem.360.cn/index/d/sid/2041682");
                //   System.out.println("1111HttpUtils");
                // HttpHandlerUtils.postLoginOrRegisterInfor("http://113.251.216.145:8080/Lianluoquan/Login");
                break;
            case R.id.send_article_activity_tool_iv_camera://拍照按钮
                // System.out.println("send_message_activity_tool_iv_camera");
                startCamera();
                break;
            case R.id.send_article_activity_preview_iv_camera://照片预览按钮
                cancelPhotoAttachment();
                break;
            case R.id.send_message_activity_preview_iv_attachment://文件预览按钮
                cancelDocumentAttachment();
                break;
            case R.id.send_article_activity_iv_send:
                sentArticle();
                break;

        }

    }

    private void cancelDocumentAttachment() {
        if (mFileAttachment != null) {
            Toast.makeText(this, "取消附件！", Toast.LENGTH_SHORT).show();
            mPreviewAttachment.setImageResource(R.mipmap.ic_plus);
            mAttachmentFiles.remove(mFileAttachment);
            mFileAttachment = null;
        }
    }

    /**
     * 发送
     */
    private void sentArticle() {
        String mUserUUID = mUserDBUtils.getUserId();//用户UUID
        String mCircleUUID = mUserDBUtils.getCircleUUID("通信与信息工程学院");//这里也还没有设计出来
        String mContent = mArticleContent.getText().toString();
        String mTitle = mArticleTitle.getText().toString();
        String tags = "通知";
        if (mContent.equals("") || mTitle.equals("")) {
            Toast.makeText(this, "请填写完整信息！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mUserUUID == null || mCircleUUID == null) {
            Toast.makeText(this, "身份信息出错无法发送！ mCircleUUID ： " + mCircleUUID + "  mUserUUID : " + mUserUUID, Toast.LENGTH_SHORT).show();
            return;
        }
        SendArticle sendArticle = new SendArticle(mUserUUID, mCircleUUID, mTitle, mContent, tags);
        String jsonString = JSONUtils.getJsonString(sendArticle);
        LogUtils.e("带发送的数据：" + jsonString);
        mHttpHandlerUtils.upLoad(SendArticleActivity.this, App.upLoadURL, mAttachmentFiles, jsonString);
        LogUtils.e("去上传啦哈哈哈！！");
        dialog.show();
    }


    /**
     * 取消图片附件，暂时处理隐藏
     */
    private void cancelPhotoAttachment() {
        if (mPhotoAttachment != null) {
            Toast.makeText(this, "取消附件！", Toast.LENGTH_SHORT).show();
            mPreviewPhoto.setImageResource(R.mipmap.ic_plus);
            mAttachmentFiles.remove(mPhotoAttachment);
            mPhotoAttachment = null;
        }
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
                    if (mPhotoAttachment != null) {
                        mAttachmentFiles.remove(mPhotoAttachment);
                        mPhotoAttachment = null;
                    }
                    mPhotoAttachment = Utils.saveBitmapToFile(imageName, image);
                    mAttachmentFiles.add(mPhotoAttachment);

                    //HttpHandlerUtils.uploadFile(file.getAbsolutePath(),"http://113.251.216.145:8080/Lianluoquan/Login");
                    //HttpHandlerUtils.upLoad("http://113.250.156.102:8080/Lianluoquan/Login", file);
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
        if (mFileAttachment != null) {
            mAttachmentFiles.remove(mFileAttachment);
            mFileAttachment = null;
        }
        mFileAttachment = new File(mUri.getPath());
        mAttachmentFiles.add(mFileAttachment);
        String fileName = mFileAttachment.getName();
        String pf = fileName.substring(fileName.lastIndexOf(".") + 1);
        setAttachmentIvRes(pf);
        LogUtils.e("attachment file name is :" + pf);

    }

    private void setAttachmentIvRes(String pf) {

        if (pf.equals("doc") || pf.equals("docx")) {
            mPreviewAttachment.setImageResource(R.mipmap.ic_file_word);
        } else if (pf.equals("xls") || pf.equals("xlsx")) {
            mPreviewAttachment.setImageResource(R.mipmap.ic_file_excel);
        } else if (pf.equals("ppt") || pf.equals("pptx")) {
            mPreviewAttachment.setImageResource(R.mipmap.ic_file_powerpoint);
        } else if (pf.equals("pdf")) {
            mPreviewAttachment.setImageResource(R.mipmap.ic_file_pdf);
        } else
            mPreviewAttachment.setImageResource(R.mipmap.ic_file_document);
    }

    @Override
    public void loginOrRegisterState(String loginState) {

    }

    @Override
    public void refreshArticleState(String refreshState) {
        dialog.dismiss();
        if (refreshState.equals("success")) {
            Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            finish();
        } else if (refreshState.equals("failure"))
            Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
    }
}
