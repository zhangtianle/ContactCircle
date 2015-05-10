package com.cqupt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqupt.bean.Circle;
import com.cqupt.bean.Friend;
import com.cqupt.contactcircle.R;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ls on 15-4-17.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private LayoutInflater mFatherInflater;
    private LayoutInflater mChildrenInflater;
    private List<String> mFatherData;
    private List<List<String>> mChildrenData;
    private List<Circle> mCircles;
    private List<Friend> mFriends;

    public ExpandableListAdapter(Context context, List<Circle> circles, List<Friend> friends) {
        this.mContext = context;
        this.mCircles = circles;
        this.mFriends = friends;
        mFatherInflater = LayoutInflater.from(mContext);
        mChildrenInflater = LayoutInflater.from(mContext);
        initData();
    }

    private void initData() {
        mFatherData = new ArrayList<String>();
        mChildrenData = new ArrayList<List<String>>();

//        mFatherData.add("首页");
        mFatherData.add("关注好友");
        mFatherData.add("联络圈");

//        List<String> one1 = new ArrayList<String>();
//        one1.add("xx");
//        one1.add("xx");
//        one1.add("xx");



        List<String> two1 = new ArrayList<String>();
        for (Friend friend : mFriends) {
            LogUtils.e("添加到Adapter的好友： "+friend);
            two1.add(friend.getName());
        }
//        for (Circle c : mCircles)
//            two1.add(c.getCircleName());
//        LogUtils.e("mCircles.size() : " + mCircles.size());

//        two1.add("XXX");
//        two1.add("XX");
//        two1.add("X");
        mChildrenData.add(two1);

        List<String> one2 = new ArrayList<String>();
        for (Circle c : mCircles)
            one2.add(c.getCircleName());
        mChildrenData.add(one2);

    }

    //重写的方法，用于获取父层的大小
    @Override
    public int getGroupCount() {

        return mFatherData == null ? 0 : mFatherData.size();
    }

    //重写的方法，用于获取父层中其中一层的子数目
    @Override
    public int getChildrenCount(int i) {
        return mChildrenData.get(i) == null ? 0 : mChildrenData.get(i).size();
    }

    //重写的方法，用于获取父层中的一项，返回的是父层的字符串类型
    @Override
    public Object getGroup(int i) {
        return mFatherData == null ? null : mFatherData.get(i);
    }


    //重写的方法，用于获取子层的内容，这里获取子层的显示字符串
    @Override
    public Object getChild(int i, int i2) {
        return mChildrenData == null ? null : mChildrenData.get(i).get(i2);
    }

    //重写的方法，用于获取父层的位置
    @Override
    public long getGroupId(int i) {
        return i;
    }

    //重写的方法，用于获取子层中单项在子层中的位置
    @Override
    public long getChildId(int i, int i2) {
        return i2;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    //重写的方法，用于获取父层的视图
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        FatherHolder fatherHolder = null;
        if (view == null) {
            view = mFatherInflater.inflate(R.layout.expand_lsiview_father, null);
            fatherHolder = new FatherHolder();
//            fatherHolder.mFatherIcon = (ImageView) view.findViewById(R.id.circle_layout_expand_list_father_iv);
            fatherHolder.mFatherName = (TextView) view.findViewById(R.id.circle_layout_expand_list_father_tx);
            view.setTag(fatherHolder);
        } else {
            fatherHolder = (FatherHolder) view.getTag();
        }
        fatherHolder.mFatherName.setText((String) getGroup(i));
        return view;
    }

    //重写的方法，用于获取子层视图
    @Override
    public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
        ChildrenHolder childrenHolder = null;
        if (view == null) {
            view = mChildrenInflater.inflate(R.layout.expand_lsiview_childer, null);
            childrenHolder = new ChildrenHolder();
            childrenHolder.mChildrenIcon = (ImageView) view.findViewById(R.id.circle_layout_expand_list_children_iv);
            childrenHolder.mChildrenName = (TextView) view.findViewById(R.id.circle_layout_expand_list_children_tx);
            view.setTag(childrenHolder);
        } else {
            childrenHolder = (ChildrenHolder) view.getTag();
        }

        childrenHolder.mChildrenName.setText((String) getChild(i, i2));
        return view;

    }

    ///////////////////////////////////////////////
    @Override
    public boolean isChildSelectable(int i, int i2) {
        return true;
    }

    static class FatherHolder {
        // ImageView mFatherIcon;
        TextView mFatherName;

    }

    static class ChildrenHolder {
        ImageView mChildrenIcon;
        TextView mChildrenName;

    }
}
