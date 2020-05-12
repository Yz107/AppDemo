package com.yz.appdemo.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yz.appdemo.R;

public class RecyclerviewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RecyclerviewAdapter() {
        super(R.layout.item_list);
        addTestData();
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        TextView user_name = helper.getView(R.id.user_name);
        user_name.setText(item);
    }

    public void addTestData() {
        addData("张三");
        addData("李四");
        addData("李四");
        addData("王五");
        addData("案说法");
        addData("案说法");
        addData("王五");
        addData("过河人");
    }
}
