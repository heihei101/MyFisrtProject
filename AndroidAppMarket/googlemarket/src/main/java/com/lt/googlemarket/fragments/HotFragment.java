package com.lt.googlemarket.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lt.googlemarket.protocol.HotProtocol;
import com.lt.googlemarket.utils.DrawableUtil;
import com.lt.googlemarket.utils.Utils;
import com.lt.googlemarket.widget.FlowLayout;
import com.lt.googlemarket.widget.LoadingPage;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/11.
 */
public class HotFragment extends BaseFragment {

    private List<String> hotInfolist;
    @Override
    protected LoadingPage.ResultState onSubLoad() {
        HotProtocol hotProtocol = new HotProtocol();
        hotInfolist = hotProtocol.getData("hot", 0);
        return checkData(hotInfolist);
    }

    @Override
    protected View onSubCreateSuccessedView() {
        ScrollView scrollView = new ScrollView(Utils.getContext());
        //对scrollview的参数进行设置
        scrollView.setPadding(Utils.dip2px(8),Utils.dip2px(8),Utils.dip2px(8),Utils.dip2px(8));//???
        //FlowLayout自定义控件-------->
        // 在内部可以添加多个TextView,并且按照是否能够排放下的规则,让TextView自动换行
        FlowLayout flowLayout=new FlowLayout(Utils.getContext()) ;
        //flowLayout中添加多个TextView
        for (int i = 0; i <hotInfolist.size() ; i++) {
            String content = hotInfolist.get(i);
            TextView textView = new TextView(Utils.getContext());
            textView.setTextColor(Color.WHITE);
            textView.setGravity(Gravity.CENTER);
            textView.setText(content);
            textView.setPadding(Utils.dip2px(8),Utils.dip2px(8),Utils.dip2px(8),Utils.dip2px(8));//有一个无效
            //red(0-255) green(0-255) blue(0-255) 构成的颜色  #00 00 00--#ff ff  ff
            int red = 30+new Random().nextInt(210);//[30,239]   //0      254
            int green = 30+new Random().nextInt(210);   //0    254
            int blue = 30+new Random().nextInt(210);   //0     254
            int rgb = Color.rgb(red, green, blue);//?????
            //java代码编写圆角矩形图片
            Drawable normalDrawable = DrawableUtil.drawDrawable(rgb, Utils.dip2px(6));
            Drawable pressDrawable = DrawableUtil.drawDrawable(Color.GRAY, Utils.dip2px(6));
            //获取一个带选择器的图片,此图片需要作为textView背景
            Drawable stateListDrawable
                    = DrawableUtil.getStateListDrawable(normalDrawable, pressDrawable);//????
            //给textview设置背景的方法有变化,所以设置判断
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                textView.setBackground(stateListDrawable);
            }else{
                textView.setBackgroundDrawable(stateListDrawable);
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            flowLayout.addView(textView);
        }
        //将自动换行的自定义控件,添加在scrollView内部
        scrollView.addView(flowLayout);
        return scrollView;
    }
}
