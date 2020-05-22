package com.yz.appdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.yz.appdemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.recyclerview_demo,R.id.progress_dialog_demo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recyclerview_demo:
                startActivity(new Intent(MainActivity.this, RecyclerviewActivity.class));
                break;
            case R.id.progress_dialog_demo:
                QMUITipDialog loading = new QMUITipDialog.Builder(this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("请稍候...")
                        .create(true,R.style.QMUI_Dialog);
//            WindowManager.LayoutParams layoutParams = loading.getWindow().getAttributes();
//            layoutParams.dimAmount = 0.2f;//背景遮罩
//            loading.getWindow().setAttributes(layoutParams);
                loading.show();
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.dismiss();
                    }
                },2000);
                break;
        }
    }
}
