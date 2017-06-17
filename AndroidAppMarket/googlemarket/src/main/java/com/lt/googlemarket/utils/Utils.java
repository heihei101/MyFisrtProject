package com.lt.googlemarket.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;

import com.lt.googlemarket.MyApplication;

/**
 * Created by Administrator on 2017/6/11.
 */
//这是一个工具类,用来存放常用的方法
public class Utils {

    public static Context getContext() {
        return MyApplication.getContext();
    }

    public static int getCurrentThreadId() {
        return MyApplication.getCurrentThreadId();
    }

    public static Thread getCurrentThread() {
        return MyApplication.getCurrentThread();
    }

    public static Handler getHandler() {
        return MyApplication.getHandler();
    }
    //布局文件转换成View对象的操作
    public static View inflact(int layoutId){
        View view = View.inflate(getContext(),layoutId,null);
        return view;
    }
    //获取资源的方法
    private static Resources getresources(){
        return getContext().getResources();
    }
    //获取图片Drawbale的方法
    public static Drawable getDrawbale(int drawableId){
        //这个地方需要进行版本判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getresources().getDrawable(drawableId,null);
        }else{
            return getresources().getDrawable(drawableId);
        }
    }
    //获取字符串资源的方法和字符串数组资源的方法,此处取到的资源id来自res/values.string是路径
    public static String getString(int stringId){
        return getresources().getString(stringId);
    }
    public static String[] getStringArray(int stringArrayId){
        return getresources().getStringArray(stringArrayId);
    }
    //写一个方法判断是在主线程运行还是在子线程运行
    public static void runOnUIThread(Runnable runnable){
        if(getCurrentThreadId()==android.os.Process.myTid()){
            //则说明在主线程
            runnable.run();
        }else{
            //在子线程
            getHandler().post(runnable);
        }
    }
    public static int dip2px(int dp){
        //dpi(像素密度) ppi(像素密度)
        //dip dp
        //density  dp和px转换的比例关系  不同的手机这个值会不一样
        float density = getresources().getDisplayMetrics().density;
        return (int)(dp*density+0.5f);
    }
}
