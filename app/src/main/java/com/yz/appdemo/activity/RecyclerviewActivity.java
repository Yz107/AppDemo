package com.yz.appdemo.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yz.appdemo.R;
import com.yz.appdemo.adapter.RecyclerviewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerviewActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private RecyclerviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerviewAdapter();
        adapter.onAttachedToRecyclerView(recyclerview);
        //上拉加载
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addTestData();
                        adapter.loadMoreComplete();
                    }
                }, 1000);

            }
        }, recyclerview);
        adapter.setEmptyView(R.layout.empty_view);
        recyclerview.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                recyclerview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNewData(null);
                        adapter.addTestData();
                        refreshLayout.finishRefresh(true);
                    }
                }, 1000);
            }
        });

    }
}
