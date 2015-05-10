package com.cqupt.contactcircle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cqupt.adapter.ListViewAdapter;
import com.cqupt.app.App;
import com.cqupt.bean.AcceptArticle;
import com.cqupt.bean.Attachment;
import com.cqupt.bean.Comment;
import com.cqupt.listener.HttpStateListener;
import com.cqupt.tool.ArticleBitmapHandlerUtils;
import com.cqupt.tool.ArticleDBUtils;
import com.cqupt.tool.HttpHandlerUtils;
import com.cqupt.tool.JSONUtils;
import com.cqupt.tool.UserInforBitmapHandlerUtils;
import com.cqupt.view.CircleImageView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ls on 15-5-2.
 */
public class ArticleInforActivity extends ActionBarActivity implements HttpStateListener, AdapterView.OnItemClickListener, TextWatcher {

    @ViewInject(R.id.article_infor_activity_lv_comment)
    private ListView mCommentsListView;

    @ViewInject(R.id.article_infor_activity_tx_circle)
    private TextView mArticleCircleTextView;
    @ViewInject(R.id.article_infor_activity_cv_user_head)
    private CircleImageView mUserHeadCircleImageView;
    @ViewInject(R.id.article_infor_activity_tx_update_time)
    private TextView mArticleUpdateTimeTextView;
    @ViewInject(R.id.article_infor_activity_tx_article_type)
    private TextView mArticleTypeTextView;
    @ViewInject(R.id.article_infor_activity_tx_article_title)
    private TextView mArticleTitleTextView;
    @ViewInject(R.id.article_infor_activity_tx_article_content)
    private TextView mArticleContentTextView;
    @ViewInject(R.id.article_infor_activity_iv_article_image)
    private ImageView mArticleImageView;
    @ViewInject(R.id.article_infor_activity_tx_article_praise)
    private TextView mArticlePraiseTextView;
    @ViewInject(R.id.article_infor_activity_tx_article_attachment)
    private TextView mArticleAttachmentsCountsTextView;
    @ViewInject(R.id.article_infor_activity_tx_article_comment)
    private TextView mArticleCommentsCountsTextView;
    @ViewInject(R.id.article_infor_activity_tx_attachment_url)
    private TextView mArticleAttachmentsURLTextView;
    @ViewInject(R.id.article_infor_activity_tx_user_name)
    private TextView mUserName;
    @ViewInject(R.id.article_infor_activity_ll_attachment_container)
    private LinearLayout mArticleAttachmentsContainer;

    @ViewInject(R.id.article_infor_activity_et_comment)
    private EditText mUserCommentsEditText;
    @ViewInject(R.id.article_infor_activity_iv_send)
    private ImageView mUserSendCommentImageView;
    @ViewInject(R.id.article_infor_activity_tb)
    private Toolbar mToolbar;

    private AcceptArticle acceptArticle;
    private BitmapUtils articleBitmapUtils;
    private BitmapUtils userInforBitmapUtils;
    private ArticleDBUtils articleDBUtils;
    private HttpHandlerUtils httpHandlerUtils;
    private List<Comment> comments;
    private List<Attachment> attachments;
    private String editTextHint;
    private String reuserUUID;
    private ListViewAdapter adapter;
    private int listViewItemHeight;
    private int listViewTotalHeight;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_infor);
        ViewUtils.inject(this);
        initToolbar();
        initData();
        initView();
        refreshArticle();
        bondViewData();

    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_navigation_up);
        setTitle("");
        mToolbar.setSubtitle("详情");
        mToolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initView() {
        mUserCommentsEditText.addTextChangedListener(this);
    }

    private void refreshArticle() {
        httpHandlerUtils.postRefreshArticle(App.downLoadURL, "refresh", acceptArticle.getId(), "1999-10-10");//////////////////////////////////
    }

    private void bondViewData() {
        if (acceptArticle == null)
            return;
        mArticleCircleTextView.setText(acceptArticle.getCircle());
        mUserName.setText(acceptArticle.getName());
        articleBitmapUtils = ArticleBitmapHandlerUtils.getBitmapUtils(this.getApplicationContext());
        userInforBitmapUtils = UserInforBitmapHandlerUtils.getBitmapUtils(this.getApplicationContext());
        userInforBitmapUtils.display(mUserHeadCircleImageView, acceptArticle.getUserHead());//文章头像
        mArticleUpdateTimeTextView.setText(acceptArticle.getTime());
        mArticleTypeTextView.setText(acceptArticle.getType());
        mArticleTitleTextView.setText(acceptArticle.getTitle());
        mArticleContentTextView.setText(acceptArticle.getContent());
        articleBitmapUtils.display(mArticleImageView, acceptArticle.getPhotoURL());
        mArticlePraiseTextView.setText(acceptArticle.getZanCount() + "");
    }

    public void initAttachmentView(List<Attachment> attachments) {

        mArticleAttachmentsCountsTextView.setText(attachments.size() + "");
        String msg = "点击下载附件：" + attachments.get(0).getAttachName();
        SpannableString msp = new SpannableString(msg);
        msp.setSpan(new URLSpan(attachments.get(0).getAttachURL()), 0, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new ForegroundColorSpan(Color.BLUE), 0, msg.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mArticleAttachmentsURLTextView.setText(msp);
        mArticleAttachmentsURLTextView.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void initData() {
        comments = new ArrayList<>();
        attachments = new ArrayList<>();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        articleDBUtils = App.getAppInstance().getArticleDBUtils();
        if (position != -1)
            acceptArticle = articleDBUtils.getArticlesFromDb().get(position);
        else
            acceptArticle = (AcceptArticle) intent.getSerializableExtra("user_article");
        httpHandlerUtils = new HttpHandlerUtils();
        httpHandlerUtils.setHttpStateListener(this);
    }

    private void initListView() {
        mCommentsListView.setOnItemClickListener(this);
        ListAdapter listAdapter = mCommentsListView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, mCommentsListView);
            listItem.measure(0, 0);
            listViewItemHeight = listItem.getMeasuredHeight();
            totalHeight += listItem.getMeasuredHeight();
        }
        listViewTotalHeight = totalHeight;
        ViewGroup.LayoutParams params = mCommentsListView.getLayoutParams();
        params.height = totalHeight + (mCommentsListView.getDividerHeight() * (adapter.getCount() - 1));
        mCommentsListView.setLayoutParams(params);
    }


    /**
     * 处理发表评论的结果
     */
    @Override
    public void postState(String loginState) {
        String infor = null;
        if (loginState != null && loginState.equals("false")) {
            Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();
        } else if (loginState != null && !loginState.equals("")) {
            LogUtils.e("发送评论的返回结果是：" + loginState);
            Comment comment = JSONUtils.parseObject(loginState, Comment.class);
            if (comment != null) {
                comments.add(comment);
                adapter = new ListViewAdapter(comments, this);
                mCommentsListView.setAdapter(adapter);
                initListView();
                acceptArticle.setComments(comments.size());//更新数据库
                articleDBUtils.updateCommentsSize(acceptArticle);
            }
            Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
            mUserCommentsEditText.setText("");
        } else
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
    }

    /**
     * 处理初次获得的Comments
     */
    @Override
    public void refreshArticleState(String refreshState) {
        if (refreshState != null && !refreshState.equals("") && !refreshState.equals("[]")) {
            List<Comment> commentList = JSONUtils.parseList(refreshState, "comments", Comment.class);
            List<Attachment> attachmentList = JSONUtils.parseList(refreshState, "attachments", Attachment.class);
            if (commentList != null && commentList.size() > 0) {
                for (Comment comment : commentList)
                    LogUtils.e("是收到去网络的刷新的评论内容是：" + comment);
                comments = commentList;

                if (comments.size() != acceptArticle.getComments()) {//去数据库更新评论的数目显示
                    acceptArticle.setComments(comments.size());
                    articleDBUtils.updateCommentsSize(acceptArticle);
                }
                mArticleCommentsCountsTextView.setText(comments.size() + "");
                adapter = new ListViewAdapter(comments, this);
                mCommentsListView.setAdapter(adapter);
                initListView();
            }
            if (attachmentList != null && attachmentList.size() > 0) {
                for (Attachment attachment : attachmentList)
                    LogUtils.e("是收到去网络的刷新附件是：" + attachment);
                attachments = attachmentList;
                initAttachmentView(attachments);
            } else {
                mArticleAttachmentsContainer.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        editTextHint = "回复+" + comments.get(i).getPubuserName() + ":";
        SpannableString ss = new SpannableString(editTextHint);
        ss.setSpan(new ForegroundColorSpan(Color.BLUE), 0, editTextHint.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, editTextHint.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);  //斜体
        mUserCommentsEditText.setText(ss);
        mUserCommentsEditText.setSelection(editTextHint.length());
        mUserCommentsEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mUserCommentsEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);//强制弹出键盘
        reuserUUID = comments.get(i).getPubuserUUID();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {


    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editTextHint != null && !editTextHint.equals("")) {
            int editStart = mUserCommentsEditText.getSelectionStart();
            int editEnd = mUserCommentsEditText.getSelectionEnd();
            if (editEnd == editTextHint.length() - 1) {
                editTextHint = "";
                reuserUUID = "";
                mUserCommentsEditText.setText("");
            }
        }

    }

    @OnClick({R.id.article_infor_activity_iv_send})
    public void onClick(View view) {
        String content = mUserCommentsEditText.getText().toString();
        String pubuserUUID = App.getAppInstance().getUserDBUtils().getUserId();
        if (editTextHint != null && !editTextHint.equals(""))
            content = content.substring(editTextHint.length() - 1);
        Comment comment = new Comment(null, reuserUUID, null, null, pubuserUUID, acceptArticle.getId(), 0, content, null, null, null);
        httpHandlerUtils.postComment(App.downLoadURL, "comment", JSON.toJSONString(comment));
    }
}
