package com.lt.googlemarket.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lt.googlemarket.JavaBeen.SubjectInfo;
import com.lt.googlemarket.R;
import com.lt.googlemarket.utils.ConstantUtil;
import com.lt.googlemarket.utils.Utils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class SubjectAdatper extends CommonAdapter<SubjectInfo> {
    public SubjectAdatper(Context context, int layoutId, List<SubjectInfo> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, SubjectInfo subjectInfo, int position) {
        holder.setText(R.id.tv_subject_des,subjectInfo.des);
        ImageView imageView = holder.getView(R.id.iv_subject);
        Glide.with(Utils.getContext())
                .load(ConstantUtil.IMGURL+subjectInfo.url)
                .placeholder(R.mipmap.ic_default)
                .crossFade(1000)
                .into(imageView);
    }
}
