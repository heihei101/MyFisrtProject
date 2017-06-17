package com.lt.googlemarket.adapter;

import android.content.Context;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */
public class CategoryAdapter extends MultiItemTypeAdapter{
    //参数一:上下文环境
    //参数二:数据集合
    public CategoryAdapter(Context context, List datas) {
        super(context, datas);
    }
}
