package com.lt.googlemarket.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lt.googlemarket.JavaBeen.CategoryInfo;
import com.lt.googlemarket.R;
import com.lt.googlemarket.adapter.CategoryAdapter;
import com.lt.googlemarket.adapter.PicTextItemViewDelegate;
import com.lt.googlemarket.adapter.TitleItemViewDelegate;
import com.lt.googlemarket.protocol.CategoryProtocol;
import com.lt.googlemarket.utils.Utils;
import com.lt.googlemarket.widget.LoadingPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/11.
 */
public class CategoryFragment extends BaseFragment {

    private List<CategoryInfo> categoryInfolist;
    private ArrayList<Object> categorylist;//因为解析出的json
    private RecyclerView rvCategory;

    @Override
    protected LoadingPage.ResultState onSubLoad() {
        CategoryProtocol categoryProtocol = new CategoryProtocol();
        categoryInfolist = categoryProtocol.getData("category", 0);
        initData();
        return checkData(categoryInfolist);
    }

    private void initData() {
        categorylist = new ArrayList<>();
        for (int i = 0; i <categoryInfolist.size() ; i++) {
            //此处添加Categroy对象需要设置格式,title在前,其他在后
            CategoryInfo categoryInfo = categoryInfolist.get(i);
            categorylist.add(categoryInfo.title);
            categorylist.addAll(categoryInfo.infos);
        }
    }

    @Override
    protected View onSubCreateSuccessedView() {
        View view = Utils.inflact(R.layout.fragment_category);
        CategoryAdapter categoryAdapter = new CategoryAdapter(Utils.getContext(),categorylist);
        rvCategory = (RecyclerView) view.findViewById(R.id.rv_category);
        //对自带文本的控件进行处理
        categoryAdapter.addItemViewDelegate(new TitleItemViewDelegate());
        //对有图片+文本的条目控件进行处理
        categoryAdapter.addItemViewDelegate(new PicTextItemViewDelegate());
        rvCategory.setLayoutManager
                (new LinearLayoutManager(Utils.getContext(),LinearLayoutManager.VERTICAL,false));
        rvCategory.setAdapter(categoryAdapter);
        return view;
    }
}
