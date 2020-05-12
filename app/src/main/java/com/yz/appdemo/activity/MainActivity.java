package com.yz.appdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

    @OnClick({R.id.recyclerview_demo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recyclerview_demo:
                startActivity(new Intent(MainActivity.this, RecyclerviewActivity.class));
                break;
        }
    }
}
