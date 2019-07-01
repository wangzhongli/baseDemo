package com.momo.basedemo.activity;

import android.os.Bundle;

import com.momo.basedemo.R;
import com.momo.basedemo.base.BaseActivity;
import com.momo.basedemo.dataSource.local.MyDatabase;

public class FirstActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }
}
