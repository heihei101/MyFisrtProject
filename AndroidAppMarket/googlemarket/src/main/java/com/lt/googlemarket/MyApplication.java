package com.lt.googlemarket;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2017/6/11.
 */

public class MyApplication extends Application {
    //要将这个类写入到AndroidManifest的application中
    //这个方法优先于MainActivity的onCreat方法执行,将常用的参数进行抽取
    private static Context context;//获取上下文
    private  static Handler handler;
    private  static Thread currentThread;//获取当前线程
    private static int currentThreadId;//获取当前线程id

    @Override
    public void onCreate() {
        context=getApplicationContext();//获取上下文
        handler = new Handler();//获取handler对象
        currentThread=Thread.currentThread();//获取当前线程
        currentThreadId=android.os.Process.myTid();//获取主线程的id
        super.onCreate();
    }

    public static Context getContext() {
        return context;
    }

    public static int getCurrentThreadId() {
        return currentThreadId;
    }

    public static Thread getCurrentThread() {
        return currentThread;
    }

    public static Handler getHandler() {
        return handler;
    }
}
