package com.momo.basedemo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.momo.basedemo.R;
import com.momo.basedemo.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<String> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBtn();
        setTitle("RecyclerView样例");
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        for (int i = 0; i < 1000; i++) {
            datas.add("测试RecyclerView性能" + i);
        }
        BaseQuickAdapter adapter = new BaseQuickAdapter<String,BaseViewHolder>(R.layout.recyclerview_item, datas) {

            @Override
            protected void convert(BaseViewHolder helper, String item) {
                helper.setText(R.id.item_tv, (CharSequence) item);
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(new Myadapter(datas));
        recyclerView.setAdapter(adapter);

    }

    public static class Myadapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public Myadapter(@Nullable List data) {
            super(R.layout.recyclerview_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.item_tv, (CharSequence) item);
        }
    }
}
