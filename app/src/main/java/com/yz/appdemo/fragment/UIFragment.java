package com.yz.appdemo.fragment;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.yz.appdemo.R;
import com.yz.appdemo.activity.AnimationActivity;
import com.yz.appdemo.activity.RecyclerviewActivity;
import com.yz.appdemo.activity.SelectPictureActivity;
import com.yz.appdemo.activity.ViewPager2Activity;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class UIFragment extends BaseFragment {
    @BindView(R.id.spinner)
    Spinner spinner;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ui;
    }

    @Override
    public void init() {
        initSpinner();
    }

    private void initSpinner() {
        ArrayList<String> areas = new ArrayList<>();
        areas.add("CNNT00256465500");
        areas.add("CNNT00256465501");
        areas.add("CNNT00256465502");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(spinner.getContext(),
                R.layout.item_spinner, areas);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.recyclerview_demo, R.id.progress_dialog_demo, R.id.anim, R.id.test2, R.id.select_picture,
            R.id.viewpager2, R.id.test3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recyclerview_demo:
                startActivity(new Intent(getActivity(), RecyclerviewActivity.class));
                break;
            case R.id.viewpager2:
                startActivity(new Intent(getActivity(), ViewPager2Activity.class));
                break;
            case R.id.select_picture:
                startActivity(new Intent(getActivity(), SelectPictureActivity.class));
                break;
            case R.id.anim:
                startActivity(new Intent(getActivity(), AnimationActivity.class));
                break;
            case R.id.test2:
                Toasty.error(getActivity(), "Error!", Toast.LENGTH_SHORT, true).show();
                break;
            case R.id.progress_dialog_demo:
                QMUITipDialog loading = new QMUITipDialog.Builder(getActivity())
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("请稍候...")
                        .create(true, R.style.QMUI_Dialog);
//            WindowManager.LayoutParams layoutParams = loading.getWindow().getAttributes();
//            layoutParams.dimAmount = 0.2f;//背景遮罩
//            loading.getWindow().setAttributes(layoutParams);
                loading.show();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                        Toast success = Toasty.error(getActivity(), "Error!", Toast.LENGTH_SHORT, true);
                        success.setGravity(Gravity.CENTER, 0, 0);
                        success.show();
                    }
                }, 2000);
                break;
        }
    }
}
