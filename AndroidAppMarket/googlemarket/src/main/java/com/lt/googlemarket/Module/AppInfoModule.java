package com.lt.googlemarket.Module;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lt.googlemarket.JavaBeen.AppInfo;
import com.lt.googlemarket.R;
import com.lt.googlemarket.utils.ConstantUtil;
import com.lt.googlemarket.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/17.
 */
public class AppInfoModule extends BaseModule<AppInfo> {

    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_app_name)
    TextView tvAppName;
    @BindView(R.id.rb_star)
    RatingBar rbStar;
    @BindView(R.id.tv_app_size)
    TextView tvAppSize;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.tv_download_state)
    TextView tvDownloadState;
    @BindView(R.id.tv_app_des)
    TextView tvAppDes;

    public AppInfoModule(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = Utils.inflact(R.layout.rv_home_item);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        Glide.with(Utils.getContext()).load(ConstantUtil.IMGURL+appInfo.iconUrl).into(ivIcon);
    }
}
