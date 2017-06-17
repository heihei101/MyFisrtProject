package com.lt.googlemarket.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lt.googlemarket.JavaBeen.AppInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class AppProtocol extends BaseProtocol<List<AppInfo>> {
    @Override
    protected List<AppInfo> parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,new TypeToken<List<AppInfo>>(){}.getType());
    }
}
