package com.lt.googlemarket.adapter;

import com.lt.googlemarket.R;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by Administrator on 2017/6/16.
 */
public class TitleItemViewDelegate implements ItemViewDelegate {
    @Override
    public int getItemViewLayoutId() {
        //只有文本条目的布局文件
        return R.layout.category_text_item;
    }

    @Override
    public boolean isForViewType(Object item, int position) {
        //确定单个条目需要用到的对象是什么
        return item instanceof String;
    }

    @Override
    public void convert(ViewHolder holder, Object string, int position) {
        //把数据放在控件上的方法
        holder.setText(R.id.tv_category,(String) string);
    }
}
