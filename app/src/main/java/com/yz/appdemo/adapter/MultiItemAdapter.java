package com.yz.appdemo.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.yz.appdemo.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class MultiItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    public MultiItemAdapter() {
        super(new ArrayList<>());
        addItemType(TYPE_LEVEL_0, R.layout.item_car_list_head);
        addItemType(TYPE_LEVEL_1, R.layout.item_car_list_lock);
        setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.expand:
                        final Level0Item lv0 = (Level0Item) getItem(position);
                        if (lv0.isExpanded()) {
                            collapse(position);
                        } else {
                            expand(position);
                        }
                        break;
                    case R.id.first_lock:
                        Toasty.info(view.getContext(), "line Click" + position).show();
                        break;
                }
            }
        });
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
        addTestData();
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final Level0Item lv0 = (Level0Item) item;
                holder.addOnClickListener(R.id.expand)
                        .addOnClickListener(R.id.first_lock)
                        .setText(R.id.plate_no, lv0.plateNo)
                        .setText(R.id.route, lv0.route)
                        .setText(R.id.first_lock_id, lv0.getFirstItem().lockNo)
                        .setText(R.id.index, String.valueOf(lv0.getFirstItem().index))
                        .setImageResource(R.id.expand, lv0.isExpanded() ? R.drawable.arrow_up : R.drawable.arrow_down);
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                holder.setText(R.id.lock_id, lv1.lockNo)
                        .setText(R.id.index, String.valueOf(lv1.index));
                break;
        }
    }

    public void addTestData() {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Level0Item lv0 = new Level0Item("京AK1234" + i, "北京海定-南京" + i);
            for (int j = 0; j < 3; j++) {
                Level1Item lv1 = new Level1Item("CNNT0024650055" + j, j + 1);
                if (j == 0) {
                    lv0.setFirstItem(lv1);
                    continue;
                }
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        addData(res);
    }

    public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity {
        public String plateNo;
        public String route;
        public Level1Item firstItem;

        public Level0Item(String plateNo, String route) {
            this.route = route;
            this.plateNo = plateNo;
        }

        @Override
        public int getItemType() {
            return TYPE_LEVEL_0;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        public void setFirstItem(Level1Item firstItem) {
            this.firstItem = firstItem;
        }

        public Level1Item getFirstItem() {
            return firstItem;
        }
    }

    public class Level1Item extends AbstractExpandableItem<String> implements MultiItemEntity {
        public int index;
        public String lockNo;

        public Level1Item(String lockNo, int index) {
            this.lockNo = lockNo;
            this.index = index;
        }

        @Override
        public int getItemType() {
            return TYPE_LEVEL_1;
        }

        @Override
        public int getLevel() {
            return 1;
        }
    }
}
