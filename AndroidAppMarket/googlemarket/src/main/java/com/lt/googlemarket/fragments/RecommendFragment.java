package com.lt.googlemarket.fragments;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.lt.googlemarket.protocol.RecommendProcotol;
import com.lt.googlemarket.random.randomlayout.StellarMap;
import com.lt.googlemarket.utils.Utils;
import com.lt.googlemarket.widget.LoadingPage;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/11.
 */
public class RecommendFragment extends BaseFragment {

    private List<String> recommendInfoList;

    @Override
    protected LoadingPage.ResultState onSubLoad() {
        RecommendProcotol recommendProcotol = new RecommendProcotol();
        recommendInfoList = recommendProcotol.getData("recommend", 0);
        return checkData(recommendInfoList);
    }

    @Override
    protected View onSubCreateSuccessedView() {
        StellarMap stellarMap = new StellarMap(Utils.getContext());//获取生成星空图的类
        //设置一个适配器对象
        MyStellarMapAdapter stellarMapAdapter  = new MyStellarMapAdapter();
        stellarMap.setAdapter(stellarMapAdapter);

        stellarMap.setGroup(0,true);//???
        stellarMap.setRegularity(5,5);//???
        return stellarMap;
    }

    private class MyStellarMapAdapter implements StellarMap.Adapter {
        public static final int PAGESIZE  = 10;//每组有是个数据
        @Override
        public int getGroupCount() {
            //返回组的总数量方法
            //如果集合数量在除以10的时候没有余数,则组的总数就是groupCount
            int groupCount = recommendInfoList.size() / PAGESIZE;
            // 如果集合数量在除以10的时候有余数,则组的总数就是groupCount+1
            if (recommendInfoList.size() % PAGESIZE != 0) {
                return groupCount + 1;
            }
            return groupCount;
        }

        @Override
        public int getCount(int group) {
            //返回group组中view个数

            ////集合的size除以10,有余数,考虑最后一组即可
            if (recommendInfoList.size() % PAGESIZE != 0) {
                //判断现在group的索引值是否指向了最后一组
                if (group == getGroupCount() - 1) {
                    //集合size对10取余数
                    //35%10 整数 3    余数5  就作为最后一组中字符串的个数
                    return recommendInfoList.size() % PAGESIZE;
                }
            }
            //集合的size除以10,如果能够整除,没有余数,返回每一页字符串的总数
            return PAGESIZE;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            //生成group组position位置的TextView
            //TextView大小随机,颜色随机

            TextView textView = new TextView(Utils.getContext());
            //这是具体获取哪个字符串的代码,
            String content = recommendInfoList.get(group * PAGESIZE + position);
            textView.setText(content);
            Random random = new Random();
            // 4-23大小
            int size = random.nextInt(16) + 12;
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
            //获取随机颜色
            int red = random.nextInt(190) + 30;
            int green = random.nextInt(190) + 30;
            int blue = random.nextInt(190) + 30;

            int rgb = Color.rgb(red, green, blue);
            textView.setTextColor(rgb);
            return textView;
        }

        @Override
        public int getNextGroupOnPan(int group, float degree) {
            //下一组飞出,使用飞出动画  0,1,2 轮循
            if(group == getGroupCount() - 1){
                group = -1;
            }
            return group + 1;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            //下一组飞入,使用飞入动画  0,1,2 轮循
            if(group == getGroupCount() - 1){
                group = -1;
            }
            return group + 1;
        }
    }
}
