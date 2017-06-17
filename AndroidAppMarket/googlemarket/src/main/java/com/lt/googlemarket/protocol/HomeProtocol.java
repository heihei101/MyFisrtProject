package com.lt.googlemarket.protocol;

import com.google.gson.Gson;
import com.lt.googlemarket.JavaBeen.HomeInfo;

/**
 * Created by Administrator on 2017/6/14.
 */

public class HomeProtocol extends BaseProtocol<HomeInfo> {
    @Override
    protected HomeInfo parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,HomeInfo.class);//对json进行解析,获取一个HomeInfo的类
    }
}
