package com.lt.googlemarket.utils;

import com.lt.googlemarket.fragments.AppFragment;
import com.lt.googlemarket.fragments.BaseFragment;
import com.lt.googlemarket.fragments.CategoryFragment;
import com.lt.googlemarket.fragments.GameFragment;
import com.lt.googlemarket.fragments.HomeFragment;
import com.lt.googlemarket.fragments.HotFragment;
import com.lt.googlemarket.fragments.RecommendFragment;
import com.lt.googlemarket.fragments.SubjectFragment;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/11.
 */
public class FragmentFactory {
    //这个工厂类根据页签的变化选择不同的fragment
    //设置一个hashmap来缓存Fragment
    public static HashMap<Integer,BaseFragment> map=new HashMap<>();
    //获取fragment的方法
    public static BaseFragment getfragment(int position){
        BaseFragment baseFragment=null;
        baseFragment=map.get(position);//从缓存中获取basefragment
        if(baseFragment!=null){
            return baseFragment;
        }else{
            //根据position索引值创建不同的Fragment,每个Framgent中oncreateView方法返回的view对象会添加至viewPager内部
            switch (position){
                case 0://首页
                    baseFragment = new HomeFragment();
                    break;
                case 1://应用
                    baseFragment = new AppFragment();
                    break;
                case 2://游戏
                    baseFragment = new GameFragment();
                    break;
                case 3://专题
                    baseFragment = new SubjectFragment();
                    break;
                case 4://推荐
                    baseFragment = new RecommendFragment();
                    break;
                case 5://分类
                    baseFragment = new CategoryFragment();
                    break;
                case 6://排行
                    baseFragment = new HotFragment();
                    break;
            }
            map.put(position,baseFragment);
            return baseFragment;
        }
    }
}
