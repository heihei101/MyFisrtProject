package com.lt.googlemarket;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lt.googlemarket.utils.FragmentFactory;
import com.lt.googlemarket.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewpager.setAdapter(new MyAdapter(getSupportFragmentManager()));//给viewpager设置适配器
        tablayout.setupWithViewPager(viewpager);//给tablayout设置viewpager对象
    }

    private class MyAdapter extends FragmentPagerAdapter {
        String[] StringArray =null;
        public MyAdapter(FragmentManager fm) {
            super(fm);
          //  此处取到的资源id来自res/values.string是路径
            StringArray= Utils.getStringArray(R.array.tab_names);
        }

        @Override
        public Fragment getItem(int position) {
            //这个方法设置具体返回
            //由于要展示的fragment有相同的特性,写一个FragmentFactory工厂类对共性进行抽取
           return FragmentFactory.getfragment(position);
           // return null;
        }

        @Override
        public int getCount() {
            return StringArray.length;
        }
        //给每个pager添加标题

        @Override
        public CharSequence getPageTitle(int position) {
            return StringArray[position];
        }
    }
}
