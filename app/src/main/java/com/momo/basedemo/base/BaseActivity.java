package com.momo.basedemo.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.momo.basedemo.R;
import com.orhanobut.logger.Logger;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


/**
 * activity 的基类
 */
public class BaseActivity extends AppCompatActivity {
    /**
     * 日志输出标志getSupportActionBar().
     **/

    protected Activity mContext;
    private TextView title;
    private ImageView back;
    protected final String TAG = this.getClass().getSimpleName();
    private LinearLayout rootLayout;

    protected void setTitle(@NonNull String msg) {
        if (title != null) {
            title.setText(msg);
        }
    }

    /**
     * sometime you want to define back event
     */
    protected void setBackBtn() {
        if (back != null) {
            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            Logger.t(TAG).e("back is null ,please check out");
        }

    }

    protected void setBackClickListener(View.OnClickListener l) {
        if (back != null) {
            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(l);
        } else {
            Logger.t(TAG).e("back is null ,please check out");
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 经测试在代码里直接声明透明状态栏更有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        mContext = this;
        // 这句很关键，注意是调用父类的方法
        super.setContentView(R.layout.activity_base);
        initToolbar();
    }

    /**
     * 设置 actionBar 隐藏或者展示
     *
     * @param isHide true 隐藏  false 展示
     */
    protected void actionBarHide(boolean isHide) {
        if (isHide) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().show();
            }
        }
    }

    /**
     * 初始化 actionbar 中的toolbar
     */
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            // Enable the Up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        }
        back = (ImageView) findViewById(R.id.img_back);
        title = (TextView) findViewById(R.id.title);
    }


    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        if (rootLayout == null) {
            return;
        }
        rootLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
    }
}

