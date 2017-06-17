package com.lt.googlemarket.protocol;

import com.google.gson.Gson;
import com.lt.googlemarket.JavaBeen.AppInfo;

/**
 * Created by Administrator on 2017/6/17.
 */

public class DetailProtocol extends BaseProtocol<AppInfo> {
    @Override
    protected AppInfo parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,AppInfo.class);
    }
}
