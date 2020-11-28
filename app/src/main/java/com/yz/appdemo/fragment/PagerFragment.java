package com.yz.appdemo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.yz.appdemo.R;
import com.yz.appdemo.util.log.Logger;

import butterknife.BindView;

public class PagerFragment extends BaseFragment {
    private final String title;
    int color;
    @BindView(R.id.root)
    ViewGroup root;
    @BindView(R.id.text_view)
    TextView textView;

    public PagerFragment(String title, int color) {
        this.title = title;
        this.color = color;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_pager;
    }

    @Override
    public void init() {
        textView.setText(title);
        root.setBackgroundColor(color);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(Logger.TAG, title + "==onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(Logger.TAG, title + "==onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(Logger.TAG, title + "==onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(Logger.TAG, title + "==onDestroy");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(Logger.TAG, title + "==onStop");
    }
}
