package com.yz.appdemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yz.appdemo.R;
import com.yz.appdemo.fragment.PagerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportActivity;

public class ViewPager2Activity extends SupportActivity {

    @BindView(R.id.viewpager2)
    ViewPager2 viewPager2;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager2);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ArrayList<PagerFragment> fragments = new ArrayList<>();
        fragments.add(new PagerFragment("Item 0", Color.RED));
        fragments.add(new PagerFragment("Item 1", Color.BLUE));
        fragments.add(new PagerFragment("Item 2", Color.DKGRAY));
        fragments.add(new PagerFragment("Item 3", Color.BLACK));
        viewPager2.setAdapter(new FragmentPagerAdapter(fragments, this));
        viewPager2.setOffscreenPageLimit(fragments.size());
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(fragments.get(position).getTitle());
        }).attach();
    }

    public static class FragmentPagerAdapter extends FragmentStateAdapter {

        private List<PagerFragment> mFragments;

        public FragmentPagerAdapter(List<PagerFragment> fragments, FragmentActivity activity) {
            super(activity);
            this.mFragments = fragments;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragments.size();
        }
    }


    @OnClick({R.id.switch_ori})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switch_ori:
                viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                break;
        }
    }
}
