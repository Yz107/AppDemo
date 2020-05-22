package com.yz.appdemo.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yz.appdemo.R;
import com.yz.appdemo.adapter.RecyclerviewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerviewActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    EasyRefreshLayout refreshLayout;
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
        //下拉刷新
        refreshLayout.setLoadMoreModel(LoadModel.NONE);
        refreshLayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
            }

            @Override
            public void onRefreshing() {
                recyclerview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setNewData(null);
                        adapter.addTestData();
                        refreshLayout.refreshComplete();
                    }
                }, 1000);
            }
        });

    }
}
