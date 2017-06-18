package com.lt.googlemarket.Module;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2017/6/17.
 */

public  abstract class BaseModule<T>{
    //由于不知道不同的fragment中有什么不同的控件,所以写一个基础模块继承
    //参数传一个泛型,以便传入不同类型的参数值
    private Context context;
    public View view;
    public BaseModule(Context ctx){
        this.context = ctx;
        view = initView();
    }
    //因为不知道每一个模块的布局,索引initView方法抽象
    public abstract View initView();
    //因为不知道有什么控件,并且不知道填充数据的类型,所以将填充UI的数据定义为任意类型泛型
    public abstract void bindData(T t);
}
