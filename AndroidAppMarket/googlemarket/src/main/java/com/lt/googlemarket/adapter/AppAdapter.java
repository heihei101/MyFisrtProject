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
 * Created by Administrator on 2017/6/15.
 */

public class AppAdapter extends CommonAdapter<AppInfo> {
    public AppAdapter(Context context, int layoutId, List<AppInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, AppInfo appInfo, int position) {
        //参数二:控件需要填充的值
        //commonAdapter使用方法,参数一是控件id,参数二是要填充的数据;
        holder.setText(R.id.tv_app_name,appInfo.name);
        holder.setText(R.id.tv_app_size, Formatter.formatFileSize(Utils.getContext(),appInfo.size));
        holder.setText(R.id.tv_app_des,appInfo.des);
        holder.setRating(R.id.rb_star,appInfo.stars);
        ImageView imageview = holder.getView(R.id.iv_icon);
        Glide.with(Utils.getContext()).load(ConstantUtil.IMGURL+appInfo.iconUrl).into(imageview);
    }
}
