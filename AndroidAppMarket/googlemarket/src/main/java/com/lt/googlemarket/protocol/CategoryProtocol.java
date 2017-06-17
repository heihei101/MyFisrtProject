package com.lt.googlemarket.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lt.googlemarket.JavaBeen.CategoryInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */

public class CategoryProtocol extends BaseProtocol<List<CategoryInfo>> {
    @Override
    protected List<CategoryInfo> parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,new TypeToken<List<CategoryInfo>>(){}.getType());
    }
}
