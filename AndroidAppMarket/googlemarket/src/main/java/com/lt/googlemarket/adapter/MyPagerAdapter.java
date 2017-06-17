package com.lt.googlemarket.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lt.googlemarket.utils.ConstantUtil;
import com.lt.googlemarket.utils.Utils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<String> data;
    public MyPagerAdapter(List<String> pictur){
        this.data = pictur;
    }
    @Override
    public int getCount() {
        /*为了展示轮播图效果并实现滑动,对getcount值进行设置*/
        return data.size()*1000;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view== object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        /*在这个方法中加载图片数据*/
        ImageView imageView = new ImageView(Utils.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片参数,自动适配
        //图片异步加载
        Glide.with(Utils.getContext()).//上下文
                load(ConstantUtil.IMGURL+data.get(position%data.size())).//图片url地址
                crossFade(300).//延时300毫秒
                into(imageView);//加载到哪个控件
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
