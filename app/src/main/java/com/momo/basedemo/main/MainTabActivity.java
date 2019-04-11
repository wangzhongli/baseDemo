package com.momo.basedemo.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.momo.basedemo.R;
import com.momo.basedemo.base.BaseActivity;
import com.momo.basedemo.main.fragment.FragmentFirst;
import com.momo.basedemo.main.fragment.FragmentFourth;
import com.momo.basedemo.main.fragment.FragmentSecond;
import com.momo.basedemo.main.fragment.FragmentThird;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainTabActivity extends BaseActivity {

    private LinearLayout llContent;

    private FragmentFirst fragment1;
    private FragmentSecond fragment2;
    private FragmentThird fragment3;
    private FragmentFourth fragment4;
    private Fragment[] fragments;
    private int lastfragment;//用于记录上个选择的Fragment


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;

                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                {
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }

                    return true;
                }
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                {
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }

                    return true;
                }
                case R.id.navigation_setting:
//                    mTextMessage.setText(R.string.title_setting);
                {
                    if (lastfragment != 3) {
                        switchFragment(lastfragment, 3);
                        lastfragment = 3;
                    }

                    return true;
                }

            }
            return false;
        }
    };
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        llContent = (LinearLayout) findViewById(R.id.llContent);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initFragment();
    }

    private void initFragment() {

        fragment1 = new FragmentFirst();
        fragment2 = new FragmentSecond();
        fragment3 = new FragmentThird();
        fragment4 = new FragmentFourth();
        fragments = new Fragment[]{fragment1, fragment2, fragment3,fragment4};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.llContent, fragment1).show(fragment1).commit();
//        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bnv);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //切换Fragment
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.llContent, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();


    }


}
