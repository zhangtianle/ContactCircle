package com.cqupt.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cqupt.bean.Circle;
import com.cqupt.bean.User;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ls on 15-4-26.
 */
public class JSONUtils {

    public static String getJsonString(Object o) {
        return JSON.toJSONString(o);
    }

    public static <T> T parseObject(String json, Class<T> t) {

        return JSON.parseObject(json, t);
    }

    public static <T> List<T> parseList(String json, String type, Class<T> t) {
        List<T> tS = new ArrayList<>();
        JSONObject object = JSON.parseObject(json);
        JSONArray array = object.getJSONArray(type);
        for (int i = 0; i < array.size(); i++) {
            LogUtils.e(" json解析得到的 :" + JSON.parseObject(array.get(i).toString(), t));
            tS.add(JSON.parseObject(array.get(i).toString(), t));
        }
        return tS;

    }
}
