package com.lt.googlemarket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.lt.googlemarket.JavaBeen.AppInfo;
import com.lt.googlemarket.Module.AppInfoModule;
import com.lt.googlemarket.protocol.DetailProtocol;
import com.lt.googlemarket.utils.Utils;
import com.lt.googlemarket.widget.LoadingPage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/17.
 */
public class DetailActivity extends AppCompatActivity {
    @BindView(R.id.fl_app_info_container)
    FrameLayout flAppInfoContainer;
    @BindView(R.id.fl_app_safe_container)
    FrameLayout flAppSafeContainer;
    @BindView(R.id.fl_app_pic_container)
    FrameLayout flAppPicContainer;
    @BindView(R.id.fl_app_des_container)
    FrameLayout flAppDesContainer;
    private String packageName;
    private AppInfo appInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        packageName = getIntent().getStringExtra("packageName");//接收意图传过来的参数
        //设置布局时,可以使用之前的代码进行复用
        LoadingPage loadingPage = new LoadingPage(Utils.getContext()) {
            @Override
            protected View onCreateSuccessedView() {
                return onSubCreateSuccessedView();
            }

            @Override
            protected ResultState onload() {
                return onSubLoad();
            }
        };
        //触发请求详情界面网络数据方法
        loadingPage.Internet();
        //loadingPage就是一个view对象,在此view对象中根据网络请求的结果
        // ,决定请求为空,请求成功,请求失败的视图展示
       // flAppInfoContainer.addView();
        setContentView(loadingPage);
    }


    private View onSubCreateSuccessedView() {
        View view = Utils.inflact(R.layout.activity_detail);
        ButterKnife.bind(this,view);
        //创建应用信息的模块对象
        AppInfoModule appInfoModule = new AppInfoModule(Utils.getContext());
        appInfoModule.bindData(appInfo);
        flAppInfoContainer.addView(appInfoModule.initView());
        return view;
    }

    private LoadingPage.ResultState onSubLoad() {
        DetailProtocol detailprotocol = new DetailProtocol();
        appInfo = detailprotocol.getData("detail", 0, "packageName=" + packageName);
        if (appInfo == null) {
            return LoadingPage.ResultState.RESULTSTATE_ERROR;
        }
        return LoadingPage.ResultState.RESULTSTATE_SUCCESSED;
    }
}
