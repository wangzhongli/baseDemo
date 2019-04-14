package com.momo.basedemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.momo.basedemo.R;
import com.momo.basedemo.base.BaseActivity;
import com.momo.basedemo.widget.decoration.GridItemDecoration;

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
//        BaseQuickAdapter adapter = new BaseQuickAdapter<String,BaseViewHolder>(R.layout.recyclerview_item, datas) {
//
//            @Override
//            protected void convert(BaseViewHolder helper, String item) {
//                helper.setText(R.id.item_tv, (CharSequence) item);
//            }
//        };

        BaseQuickAdapter adapter = new Myadapter(datas);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.addItemDecoration(new GridSectionAverageGapItemDecoration(50,20,20,20));
        recyclerView.addItemDecoration(new GridItemDecoration(mContext,R.color.colorAccent));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(mContext, datas.get(position),Toast.LENGTH_SHORT).show();

            }
        });

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
