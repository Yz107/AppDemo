package com.yz.appdemo.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.yz.appdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

public class AnimationActivity extends SupportActivity {

    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.image2)
    ImageView imageView2;
    @BindView(R.id.image3)
    ImageView imageView3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
        startAnim();
    }

    private void startAnim() {
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
        Animation rotate2 = AnimationUtils.loadAnimation(this, R.anim.rotate_anim2);
        Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
        Interpolator interpolator = new Interpolator() {
            @Override
            public float getInterpolation(float input) {
                if (input > 0.5) return 1 - input;
                return input;
            }
        };
        translate.setInterpolator(interpolator);
        imageView.startAnimation(rotate);
        imageView2.startAnimation(rotate2);
        imageView3.startAnimation(translate);
    }
}
