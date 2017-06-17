package com.lt.googlemarket.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.itheima.xrefreshlayout.XRefreshLayout;
import com.lt.googlemarket.JavaBeen.AppInfo;
import com.lt.googlemarket.R;
import com.lt.googlemarket.adapter.AppAdapter;
import com.lt.googlemarket.protocol.AppProtocol;
import com.lt.googlemarket.utils.Utils;
import com.lt.googlemarket.widget.LoadingPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/11.
 */
public class AppFragment extends BaseFragment {

    private List<AppInfo> appInfoList = new ArrayList<>();
    private RecyclerView rvApp;
    private XRefreshLayout xrApp;
    private AppAdapter appAdapter;

    @Override
    protected LoadingPage.ResultState onSubLoad() {
        AppProtocol appProtocol = new AppProtocol();
        List<AppInfo> appInfo = appProtocol.getData("app", 0);
        if(appProtocol==null){
            return LoadingPage.ResultState.RESULTSTATE_ERROR;
        }
        appInfoList.addAll(appInfo);
        return checkData(appInfo);
    }

    @Override
    protected View onSubCreateSuccessedView() {
        View view = Utils.inflact(R.layout.fragment_app);
        rvApp = (RecyclerView) view.findViewById(R.id.rv_app);
        xrApp = (XRefreshLayout) view.findViewById(R.id.xrefreshLayout_app);
        appAdapter = new AppAdapter(
                Utils.getContext(), R.layout.rv_app_item, appInfoList);
        rvApp.setLayoutManager(new LinearLayoutManager
                (Utils.getContext(), LinearLayoutManager.VERTICAL, false));
        rvApp.setAdapter(appAdapter);
        xrApp.setOnRefreshListener(new XRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData(true);
            }

            @Override
            public void onLoadMore() {
                loadData(false);
            }
        });
        return view;
    }

    private void loadData(final boolean state) {
        if(state){
            appInfoList.clear();
        }
        AppProtocol appProtocol = new AppProtocol();
        List<AppInfo> applist = appProtocol.getData("app", appInfoList.size());
        appInfoList.addAll(applist);
        Utils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                appAdapter.notifyDataSetChanged();
                xrApp.completeRefresh();
            }
        });
    }
}
