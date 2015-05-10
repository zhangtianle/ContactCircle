package com.cqupt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cqupt.app.App;
import com.cqupt.bean.Circle;
import com.cqupt.bean.Comment;
import com.cqupt.contactcircle.R;
import com.cqupt.listener.HttpStateListener;
import com.cqupt.tool.HttpHandlerUtils;
import com.cqupt.tool.JSONUtils;
import com.cqupt.tool.UserDBUtils;
import com.cqupt.tool.UserInforBitmapHandlerUtils;
import com.cqupt.view.CircleImageView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.util.LogUtils;

import java.util.List;

/**
 * Created by ls on 15-4-30.
 */
public class SearchCircleListViewAdapter extends BaseAdapter implements HttpStateListener {
    private List<Circle> circles;
    //   private ArticleDBUtils articleDBUtils;
    //  private String articleUUID;
    private BitmapUtils mUserBitmapUtils;
    private HttpHandlerUtils mHttpHandlerUtils;
    private String userUUID;
    private UserDBUtils mUserDBUtils;
    private Context mContext;

    public SearchCircleListViewAdapter(List<Circle> circles, Context context) {
        //    this.articleUUID = articleUUID;
        //     articleDBUtils = App.getAppInstance().getArticleDBUtils();
        mUserDBUtils = App.getAppInstance().getUserDBUtils();
        userUUID = mUserDBUtils.getUserId();
        this.circles = circles;
        this.mContext = context;
        mHttpHandlerUtils = new HttpHandlerUtils();
        mHttpHandlerUtils.setHttpStateListener(this);
        mUserBitmapUtils = UserInforBitmapHandlerUtils.getBitmapUtils(context.getApplicationContext());
    }


    @Override
    public int getCount() {
        return circles.size();
    }

    @Override
    public Object getItem(int position) {
        return circles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Circle circle = circles.get(position);
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_list_view_item, null);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.search_activity_list_view_tx_name);
            mViewHolder.mHead = (CircleImageView) convertView.findViewById(R.id.search_activity_list_view_cv);
            mViewHolder.mAdd = (Button) convertView.findViewById(R.id.search_activity_list_view_btn_add);
            convertView.setTag(mViewHolder);
        } else
            mViewHolder = (ViewHolder) convertView.getTag();
        mViewHolder.mName.setText(circle.getCircleName());
        mUserBitmapUtils.display(mViewHolder.mHead, circle.getCircleHead());
        mViewHolder.mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHttpHandlerUtils.follow(App.downLoadURL, "follow", "circle", circle.getId(), App.getAppInstance().getUserDBUtils().getUserId());


            }
        });

        return convertView;
    }

    @Override
    public void postState(String loginState) {
        if (!loginState.equals("false")) {
            Circle circle = JSONUtils.parseObject(loginState, Circle.class);
            if (circle != null) {
                LogUtils.e("关注的ciecle是： " + circle.toString());
                circle.setUserUUID(userUUID);
                mUserDBUtils.saveUserCircleToDb(circle);
                setToast("关注成功");
            } else
                setToast("关注失败");
        }else
            setToast("关注失败");
    }

    private void setToast(String s) {
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void refreshArticleState(String refreshState) {

    }


    static class ViewHolder {
        TextView mName;
        CircleImageView mHead;
        Button mAdd;
    }
}
