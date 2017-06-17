package com.lt.googlemarket.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lt.googlemarket.JavaBeen.AppInfo;
import com.lt.googlemarket.R;
import com.lt.googlemarket.utils.ConstantUtil;
import com.lt.googlemarket.utils.Utils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class HomeAdapter extends CommonAdapter<AppInfo> {
    public HomeAdapter(Context context, int layoutId, List<AppInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, AppInfo appInfo, int position) {
        //appInfo 一个条目需要使用到的对象
        //position每个条目的索引值
        //holder 继承至RecycyclerView.ViewHolder
        //此方法作用就是给控件填充数据即可
        //参数一:控件id
        //参数二:控件需要填充的值
        holder.setText(R.id.tv_app_name,appInfo.name);
        holder.setText(R.id.tv_app_size,
                Formatter.formatFileSize(Utils.getContext(),appInfo.size));
        holder.setText(R.id.tv_app_des,appInfo.des);
        holder.setRating(R.id.rb_star,appInfo.stars);

        //获取图片控件
        ImageView ivIcon = holder.getView(R.id.iv_icon);
        Glide.with(Utils.getContext()).load(ConstantUtil.IMGURL+appInfo.iconUrl).into(ivIcon);
    }
}
