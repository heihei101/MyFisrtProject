package com.lt.googlemarket.fragments;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.itheima.xrefreshlayout.XRefreshLayout;
import com.lt.googlemarket.DetailActivity;
import com.lt.googlemarket.JavaBeen.AppInfo;
import com.lt.googlemarket.JavaBeen.HomeInfo;
import com.lt.googlemarket.R;
import com.lt.googlemarket.adapter.HomeAdapter;
import com.lt.googlemarket.adapter.MyPagerAdapter;
import com.lt.googlemarket.protocol.HomeProtocol;
import com.lt.googlemarket.utils.Utils;
import com.lt.googlemarket.widget.LoadingPage;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/11.
 */
public class HomeFragment extends BaseFragment {
    RecyclerView rvHome;
    XRefreshLayout xrefreshLayout;//用这个包裹RecyclerView,实现下拉加载,上拉刷新功能
    private List<AppInfo> appInfoList = new ArrayList<AppInfo>();
    private List<String> picList;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private LinearLayout llDotContainer;
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private HomeAdapter homeadapter;

    @Override
    protected LoadingPage.ResultState onSubLoad() {
        picList = new ArrayList<>();//获取oicList的集合对象
        //在此处请求首页数据,将首页结果告知onSubLoad方法
        //开启一个http请求,获取json,json解析
        HomeProtocol homeProtocol = new HomeProtocol();
        HomeInfo homeInfo = homeProtocol.getData("home", 0);
        if (homeInfo == null){
            return LoadingPage.ResultState.RESULTSTATE_ERROR;//当没取到数据时,返回失败
        }
        appInfoList.addAll(homeInfo.list);//20条
        picList.addAll(homeInfo.picture);//轮播图图片的集合
        return checkData(homeInfo.list);
    }
    @Override
    protected View onSubCreateSuccessedView() {
        View view = Utils.inflact(R.layout.fragment_home);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        xrefreshLayout = (XRefreshLayout) view.findViewById(R.id.xrefreshLayout);
   //      unbinder = ButterKnife.bind(this, view);
        //不能放在子线程中
        homeadapter = new HomeAdapter(Utils.getContext(),R.layout.rv_home_item,appInfoList);
        //RecyclerView必须对属性进行设置,上下文,方向
        rvHome.setLayoutManager(new LinearLayoutManager
                (Utils.getContext(), LinearLayoutManager.VERTICAL, false));
        /*由于RecyclerView没有单条目点击事件,所以将点击事件的监听加到适配器或者view中
        * 这里我们加到条目中*/
        homeadapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent =new Intent(Utils.getContext(),DetailActivity.class);
                intent.putExtra("packageName",appInfoList.get(position-1).packageName);//将包名作为参数传给activity
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        //设置方法添加轮播图
        initHear();

        xrefreshLayout.setOnRefreshListener(new XRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                loadData(true);
            }

            @Override
            public void onLoadMore() {
               //上拉加载
                loadData(false);
            }
        });
        return view;
    }

    private void initHear() {
        //构造方法的参数,必须是简便
        headerAndFooterWrapper = new HeaderAndFooterWrapper(homeadapter);
        View view = Utils.inflact(R.layout.layout_header);//轮播图的布局文件
        //这是轮播图右下方灰点
        llDotContainer = (LinearLayout) view.findViewById(R.id.ll_dot_container);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);//切换控件
        //添加的是轮播图和点2块内容,所以添加点和viewpager所在的父容器
        headerAndFooterWrapper.addHeaderView(view);//将view添加到headerAndFooterWrapper头部
        rvHome.setAdapter(headerAndFooterWrapper);//给RecyclerView添加一个适配器
        headerAndFooterWrapper.notifyDataSetChanged();//适配器数据更新
        initViewPager();//这个方法用来添加轮播图
        initDot();//添加灰点
        startRoll();//开始轮播图
    }

    private void startRoll() {
        RunnableTask runnableTask = new RunnableTask();
        runnableTask.start();
    }
    class RunnableTask implements Runnable {
        public void start() {
            //防止2个任务管理轮播图轮转,每次开启之前都将之前的轮播图刷掉
            Utils.getHandler().removeCallbacksAndMessages(null);
            //参数一:runnable接口的实现类的对象,此处传递了方法所在类的对象
            //参数二:延时时间
            Utils.getHandler().postDelayed(this, 3000);
        }

        @Override
        public void run() {
            //会在3秒钟后触发,在3秒钟后,需要让图片轮循一次
            int currentPosition = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(currentPosition);
            //当开启下一次的任务之前，需要将上一次已经做完的任务，清空
            Utils.getHandler().removeCallbacks(this);
            //在3秒钟后右开启一次图片的轮转
            Utils.getHandler().postDelayed(this, 3000);
        }
    }
    private void initDot() {
        //给viewpager添加翻页动画
//        ABaseTransformer
        //参数一：绘制viewpager中图片的顺序
        //参数二：用于指定动画类型,这个是从系统中获取的
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setCurrentItem(picList.size() * 1000 / 2);//初始化图片的起点位置
        //清空线性布局中所有的点
        llDotContainer.removeAllViews();
        //对LinearLayout进行参数设置,自适应大小
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, Utils.dip2px(4), Utils.dip2px(4));//设置控件大小
        //清空后,循环几次添加几个点
        for (int i = 0; i < picList.size(); i++) {
            ImageView imageView = new ImageView(Utils.getContext());
            if (i == 0) {
                //给点设置默认值
                imageView.setImageResource(R.mipmap.indicator_selected);
            } else {
                imageView.setImageResource(R.mipmap.indicator_normal);
            }
            llDotContainer.addView(imageView, params);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < picList.size(); i++) {
                    ImageView imageView = (ImageView) llDotContainer.getChildAt(i);
                    position = position%picList.size();
                    if(i == position){
                        //第一个点是默认选中的点,默认选中点用白色点
                        imageView.setImageResource(R.mipmap.indicator_selected);
                    }else{
                        imageView.setImageResource(R.mipmap.indicator_normal);
                    }
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    private void initViewPager() {
        myPagerAdapter = new MyPagerAdapter(picList);//为轮播图准备集合对象
        viewPager.setAdapter(myPagerAdapter);//为轮播图配置适配器
    }

    private void loadData(final boolean state) {
        //由于刷新加载可能读取网络数据,所以要开启子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(state){
                    //刷新即清空适配器,并重新加载数据
                    //不清空即说明加载
                    appInfoList.clear();
                    picList.clear();//刷新轮播图
                //    System.out.println("111111111111");
                }
                HomeProtocol homeprotocol = new HomeProtocol();
                HomeInfo homeinfo = homeprotocol.getData("home",appInfoList.size());
            //    System.out.println(appInfoList.size()+"");
                appInfoList.addAll(homeinfo.list);
                if (state){
                    //如果是刷新才需要重新给picList中添加轮播图图片
                    picList.addAll(homeinfo.picture);
                }
                //数据获取成功后,要刷新UI,在主线程进行
                Utils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                     //   if(state){
                        if (state){
                            //再刷新的时候，轮播图的数据适配器需要进行刷新
                            myPagerAdapter.notifyDataSetChanged();
                            //点回到第0个选中
                            initDot();
                            //开启点的轮循任务
                            startRoll();
                        }
                        //刷新数据适配器UI操作
                        //因为现在数据适配器中添加了头,刷新的数据适配器就是带头带脚的数据适配器
                        headerAndFooterWrapper.notifyDataSetChanged();
                            //刷新数据时
                            xrefreshLayout.completeRefresh();
                        //}
                    }
                });
            }
        }).start();
    }
}
