package com.lt.googlemarket.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */

public class RecommendProcotol extends BaseProtocol<List<String>> {
    @Override
    protected List<String>  parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,new TypeToken<List<String>>(){}.getType());
    }
}
