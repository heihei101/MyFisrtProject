package com.lt.googlemarket.fragments;

import android.view.View;

import com.lt.googlemarket.widget.LoadingPage;

/**
 * Created by Administrator on 2017/6/11.
 */
public class GameFragment extends BaseFragment {
    @Override
    protected LoadingPage.ResultState onSubLoad() {
        return LoadingPage.ResultState.RESULTSTATE_ERROR;
    }

    @Override
    protected View onSubCreateSuccessedView() {
        return null;
    }
}
