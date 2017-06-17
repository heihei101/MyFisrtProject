package com.lt.googlemarket.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.itheima.xrefreshlayout.XRefreshLayout;
import com.lt.googlemarket.JavaBeen.SubjectInfo;
import com.lt.googlemarket.R;
import com.lt.googlemarket.adapter.SubjectAdatper;
import com.lt.googlemarket.protocol.SubjectProtocol;
import com.lt.googlemarket.utils.Utils;
import com.lt.googlemarket.widget.LoadingPage;

import java.util.List;

/**
 * Created by Administrator on 2017/6/11.
 */
public class SubjectFragment extends BaseFragment {
    private List<SubjectInfo> subjectlist;
    private XRefreshLayout xrefreshLayout_subject;
    private RecyclerView rvSubject;
    private SubjectAdatper subjectAdatper;

    @Override
    protected LoadingPage.ResultState onSubLoad() {
        SubjectProtocol subjectProtocol = new SubjectProtocol();
        subjectlist = subjectProtocol.getData("subject", 0);
        if(subjectlist==null){
            return LoadingPage.ResultState.RESULTSTATE_ERROR;
        }
        return checkData(subjectlist);
    }

    @Override
    protected View onSubCreateSuccessedView() {
        View view = Utils.inflact(R.layout.fragment_subject);
        xrefreshLayout_subject = (XRefreshLayout) view.findViewById(R.id.xrefreshLayout_subject);
        rvSubject = (RecyclerView) view.findViewById(R.id.rv_subject);
        rvSubject.setLayoutManager(new LinearLayoutManager(Utils.getContext(),
                LinearLayoutManager.VERTICAL, false));
        subjectAdatper = new SubjectAdatper(Utils.getContext(), R.layout.rv_subject_item,subjectlist);
        rvSubject.setAdapter(subjectAdatper);
        xrefreshLayout_subject.setOnRefreshListener(new XRefreshLayout.OnRefreshListener() {
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(state){
                    subjectlist.clear();
                }
                SubjectProtocol subjectProtocol = new SubjectProtocol();
                List<SubjectInfo> subjectInfoList =subjectProtocol.getData("subject",subjectlist.size());
                subjectlist.addAll(subjectInfoList);
                Utils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        subjectAdatper.notifyDataSetChanged();
                        xrefreshLayout_subject.completeRefresh();
                    }
                });
            }
        }).start();
    }
}
