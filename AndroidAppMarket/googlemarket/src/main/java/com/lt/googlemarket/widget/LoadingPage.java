package com.lt.googlemarket.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.lt.googlemarket.R;
import com.lt.googlemarket.utils.ConstantUtil;
import com.lt.googlemarket.utils.Utils;

/**
 * Created by Administrator on 2017/6/11.
 */

public  abstract class LoadingPage extends FrameLayout {
    private View loadingView;//加载过程中的视图
    private View errorView;//加载失败的视图
    private View emptyView;//服务器没有返回数据的视图
    private View successedView;//请求服务器成功,也有数据时候展示的视图
    //提供一个变量用于记录本次请求网络的所处状态值
    //static在内存中只会保留一个变量
    //非static在内存中可以保留多个变量,因为项目中每一个页面都需要记录此页面的网络请求状态,所以页面有几个,变量就有几个
    private int current_state = ConstantUtil.STATE_NONE;
    public LoadingPage(Context context) {
        this(context,null);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //最后都会执行这个方法,所以在这个方法中调用initview方法
        //System.out.println("success1");
        initview();
    }
    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs,-1);

    }
    private void initview() {
        /**
         * 将在此帧布局中可能会展示的界面添加进来
         *      加载过程中的view对象
         *      加载失败view对象
         *      加载为空view对象
         */
        //将控件加载进fragment
        loadingView= Utils.inflact(R.layout.layout_loading);
        addView(loadingView);
        errorView= Utils.inflact(R.layout.layout_error);
        addView(errorView);
        emptyView= Utils.inflact(R.layout.layout_empty);
        addView(emptyView);
        //在什么情况下展示何种视图效果
        //1.(正在加载,初始状态------->进度条转圈)
        //2.(请求失败------------->错误wifi符号)
        //3.(请求成功,数据为空------------->空篮子)
        //4.(请求成功------------->由具体的Fragment决定页面效果)
       // System.out.println("success");
        showPage();
    }

    private void showPage() {
        if(loadingView!=null){
            if(current_state==ConstantUtil.STATE_NONE ||
                    current_state==ConstantUtil.STATE_LOADING){
                //此时显示进度条,current_state=0or1;
                loadingView.setVisibility(View.VISIBLE);
            }else{
                loadingView.setVisibility(View.GONE);
            }
        }
        if(errorView!=null){
            if(current_state==ConstantUtil.STATE_ERROR){
                errorView.setVisibility(View.VISIBLE);
            }else{
                errorView.setVisibility(View.GONE);
            }
        }
        if(emptyView!=null){
            if(current_state==ConstantUtil.STATE_EMPTY ){
                emptyView.setVisibility(View.VISIBLE);
            }else{
                emptyView.setVisibility(View.GONE);
            }
        }
        //如果请求网络的状态,请求成功,服务器也提供了数据
        if(current_state==ConstantUtil.STATE_SUCCESSED){
            //由于当获取网络成功后,会产生不同的布局,所以要根据不同的情况进行设置
            //设置抽象类
            successedView=onCreateSuccessedView();
            if(successedView!=null){
                addView(successedView);
                successedView.setVisibility(View.VISIBLE);
            }
        }
    }
    protected   abstract View onCreateSuccessedView();
    //写一个方法来访问网络
    public void Internet(){
        //当current_state处于非loading状态时可以访问网络,将current_state状态归位
        if (current_state == ConstantUtil.STATE_SUCCESSED || current_state == ConstantUtil.STATE_EMPTY ||
                current_state == ConstantUtil.STATE_ERROR){
            current_state = ConstantUtil.STATE_NONE;
        }
        //2.如果状态为STATE_NONE就可以发送下一次请求
        if(current_state==ConstantUtil.STATE_NONE){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //针对不同的Fragment和不同的activity发送网络请求
                    //知道url地址?,请求参数?,请求方式?,请求结果json串?
                    //(失败,为空,成功)
                    //1.获取一个枚举的对象
                    final ResultState resultState=onload();
                    //由于要根据网络结果返回ui,所以以下代码在主线程执行
                    Utils.runOnUIThread(new Runnable() {
                        @Override
                        public void run() {
                            //2.获取在创建枚举的时候,传递进来的参数(请求网络的状态码)
                            if(resultState!=null){
                                current_state=resultState.getState();
                                showPage();//回调showPage方法刷新界面
                            }
                        }
                    });
                }
            }).start();
        }
    }

    // 枚举(限定类型,限定onLoad方法的结果的类型,限定了onLoad方法返回的对象,只可能是以下3个对象中的某一个
    /*RESULTSTATE_ERROR(2),
    RESULTSTATE_EMPTY(3),
    RESULTSTATE_SUCCESSED(4);*/
    protected abstract ResultState onload();
    public enum ResultState{
        //共同的状态有三种
        RESULTSTATE_ERROR(ConstantUtil.STATE_ERROR),
        RESULTSTATE_EMPTY(ConstantUtil.STATE_EMPTY),
        RESULTSTATE_SUCCESSED(ConstantUtil.STATE_SUCCESSED);
        private int state;
        private ResultState(int state){
            this.state=state;
        }
        public int getState(){
            return state;
        }
    }
}
