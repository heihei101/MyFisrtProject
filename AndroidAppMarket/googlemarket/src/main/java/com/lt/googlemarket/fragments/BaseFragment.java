package com.lt.googlemarket.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lt.googlemarket.utils.Utils;
import com.lt.googlemarket.widget.LoadingPage;

import java.util.List;

/**
 * Created by Administrator on 2017/6/11.
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       //这个方法加载fragment中的view
        //对代码进行优化,将共性进行抽取,抽取出来的类就是LoadingPage
        //由loadingPage管理好的4中界面展示情况的某一种
        LoadingPage loadingPage = new LoadingPage(Utils.getContext()) {
            @Override
            protected View onCreateSuccessedView() {
                //BaseFragment每一个子类(子类Fragment 首页,应用......)在请求成功时候,展示页面效果
                //再抽象
                return onSubCreateSuccessedView();
            }
            @Override
            protected LoadingPage.ResultState onload() {
                //给首页,应用,游戏界面请求网络的方法
                return onSubLoad();
            }
        };
        //请求网络,将网络的数据填充loadingPage中成功的view对象
        loadingPage.Internet();
        return loadingPage;
    }
    /**
     * 所有的子类的Fragment都需要进行数据是否为null,大小为0,大小大于0检测,所以检测方法放置在父类中
     * @param
     * @return
     */
    protected LoadingPage.ResultState checkData(List list) {
        if (list!=null){
            if (list.size()>0){
                //请求成功,服务器给数据了
                return LoadingPage.ResultState.RESULTSTATE_SUCCESSED;
            }
            //请求成功,但是服务器没有给数据
            return LoadingPage.ResultState.RESULTSTATE_EMPTY;
        }
        //请求失败
        return LoadingPage.ResultState.RESULTSTATE_ERROR;
    }
    protected abstract LoadingPage.ResultState onSubLoad();
    protected abstract View onSubCreateSuccessedView();
}
