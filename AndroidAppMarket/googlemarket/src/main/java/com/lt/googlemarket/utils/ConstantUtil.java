package com.lt.googlemarket.utils;

/**
 * Created by Administrator on 2017/6/11.
 */

public class ConstantUtil {
    //这个类负责管理常量
    //这是判断网络状态的常量
    public static final int STATE_NONE = 0;//初始状态
    public static final int STATE_LOADING = 1;//正在加载状态
    public static final int STATE_ERROR = 2;//加载失败状态
    public static final int STATE_EMPTY = 3;//加载数据为空状态
    public static final int STATE_SUCCESSED = 4;//加载成功状态
    public static final String BASEURL = "http://127.0.0.1:8090/";//这个是服务器地址
    public static final String IMGURL = "http://127.0.0.1:8090/image?name=";//这个是服务器中图片的文件夹地址
    public static final long FILETIMEOUT = 60*15*1000;//缓存文件的时间长短
}
