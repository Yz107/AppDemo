package com.yz.appdemo.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.yz.appdemo.R;
import com.yz.appdemo.fragment.SettingFragment;
import com.yz.appdemo.fragment.UIFragment;
import com.yz.appdemo.util.PermissionUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity {
    //应用所需权限
    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};

    @BindView(R.id.fragment_container)
    FrameLayout container;
    @BindView(R.id.navigation)
    RadioGroup mNavigation;

    //当前显示的fragment
    private SupportFragment mShowFragment;
    private SupportFragment uiFragment;
    private SupportFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        PermissionUtil.requestPermissions(this, permissions);
        initView();
    }


    private void initView() {
        uiFragment = new UIFragment();
        settingFragment = new SettingFragment();
        loadMultipleRootFragment(R.id.fragment_container, 0,
                uiFragment, settingFragment);
        mShowFragment = uiFragment;
        mNavigation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.monitor:
                        show(uiFragment);
                        break;
                    case R.id.communication:
                        show(settingFragment);
                        break;
                    case R.id.alarm:
                        show(uiFragment);
                        break;
                    case R.id.settings:
                        show(settingFragment);
                        break;
                }
            }
        });
    }


    private void show(SupportFragment showFragment) {
        showHideFragment(showFragment, mShowFragment);
        mShowFragment = showFragment;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }


}
