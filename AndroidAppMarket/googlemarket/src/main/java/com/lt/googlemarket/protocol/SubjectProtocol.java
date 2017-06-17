package com.lt.googlemarket.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lt.googlemarket.JavaBeen.SubjectInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>> {
    @Override
    protected List<SubjectInfo> parseJson(String json) {
        Gson gson =new Gson();
        return gson.fromJson(json,new TypeToken<List<SubjectInfo>>(){}.getType());
    }
}
