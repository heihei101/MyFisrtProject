package com.lt.googlemarket;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lt.googlemarket.utils.ConstantUtil;
import com.lt.googlemarket.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2017/6/18.
 */
public class BigPicActivity extends Activity {

    @BindView(R.id.vp_pic)
    ViewPager vpPic;
   // private ViewPager viewPager;
    private int index;
    private ArrayList<String> imgUrlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigpic);
        ButterKnife.bind(this);
      //  viewPager = (ViewPager) Utils.inflact(R.id.vp_pic);
        //viewpager需要展示那个索引位置的图片
        index = getIntent().getIntExtra("index", 0);
        //前一个界面传递过来的图片链接地址集合
        imgUrlList = getIntent().getStringArrayListExtra("imgUrlList");
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        vpPic.setAdapter(myPagerAdapter);
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imgUrlList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(Utils.getContext());
            Glide.with(Utils.getContext()).load(ConstantUtil.IMGURL + imgUrlList.get(position))
                    .into(photoView);
            photoView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(photoView);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
