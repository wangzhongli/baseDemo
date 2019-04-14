package com.momo.basedemo.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.momo.basedemo.R;
import com.momo.basedemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBtn();
        setTitle("RecyclerView样例");
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);
    }
}
