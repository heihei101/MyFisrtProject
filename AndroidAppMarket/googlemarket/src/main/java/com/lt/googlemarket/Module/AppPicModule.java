package com.lt.googlemarket.Module;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.lt.googlemarket.BigPicActivity;
import com.lt.googlemarket.JavaBeen.AppInfo;
import com.lt.googlemarket.R;
import com.lt.googlemarket.utils.ConstantUtil;
import com.lt.googlemarket.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/18.
 */

public class AppPicModule extends BaseModule <AppInfo>{
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.hsv)
    HorizontalScrollView hsv;
    private AppInfo mappInfo;

    public AppPicModule(Context ctx) {
        super(ctx);
    }

    @Override
    public View initView() {
        View view = Utils.inflact(R.layout.layout_app_pic);
     //   System.out.println("22222222222222222222222222");
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void bindData(AppInfo appInfo) {
        this.mappInfo = appInfo;
        List<String> screenList = appInfo.screen;
        System.out.println("111111111111111111111"+screenList.size());
        int dip2px150 = Utils.dip2px(150);
        int dip2px90 = Utils.dip2px(90);
        LinearLayout.LayoutParams params
                = new LinearLayout.LayoutParams(dip2px90, dip2px150);
        //对线性布局的间距参数进行设置,左 上 右 下 ,边距都是6
        params.setMargins(Utils.dip2px(6),Utils.dip2px(6),0,Utils.dip2px(6));
       // System.out.println("111111111111111111111"+screenList.size());
        for (int i = 0; i <screenList.size(); i++) {
            String imgurl = screenList.get(i);
            final int index =i;
            ImageView imageview = new ImageView(Utils.getContext());
            Glide.with(Utils.getContext()).load(ConstantUtil.IMGURL+imgurl).into(imageview);
            llContainer.addView(imageview,params);
            //给每个图片添加点击事件
            imageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //开启一个新的界面展示大图
                    Intent intent =new Intent(Utils.getContext(),BigPicActivity.class);
                    //把具体点的哪个图片传给activity
                    intent.putExtra("index",index);
                    intent.putStringArrayListExtra("imgUrlList", (ArrayList<String>) mappInfo.screen);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getContext().startActivity(intent);
                }
            });
        }
    }
}
