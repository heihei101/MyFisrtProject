package com.lt.googlemarket.Module;

import android.content.Context;
import android.text.format.Formatter;
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


    @BindView(R.id.iv_app_icon)
    ImageView ivAppIcon;
    @BindView(R.id.tv_app_name)
    TextView tvAppName;
    @BindView(R.id.rb_app_star)
    RatingBar rbAppStar;
    @BindView(R.id.tv_download)
    TextView tvDownload;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_size)
    TextView tvSize;

    public AppInfoModule(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = Utils.inflact(R.layout.layout_app_info);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        tvAppName.setText(appInfo.name);
        tvDownload.setText(appInfo.downloadNum);
        tvVersion.setText(appInfo.version);
        tvTime.setText(appInfo.date);
        tvSize.setText(Formatter.formatFileSize(Utils.getContext(),appInfo.size));

        rbAppStar.setRating(appInfo.stars);
        Glide.with(Utils.getContext()).load(ConstantUtil.IMGURL+appInfo.iconUrl).into(ivAppIcon);
        // tvAppName.setText("aaaaaaaaaa");
        // Glide.with(Utils.getContext()).load(ConstantUtil.IMGURL+appInfo.iconUrl).into(ivIcon);
    }
}
