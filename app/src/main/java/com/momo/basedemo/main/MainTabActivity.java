package com.momo.basedemo.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.momo.basedemo.R;
import com.momo.basedemo.base.BaseActivity;
import com.momo.basedemo.bean.Task;
import com.momo.basedemo.dataSource.Injection;
import com.momo.basedemo.dataSource.TaskDataSource;
import com.momo.basedemo.dataSource.TasksRepository;
import com.momo.basedemo.dataSource.local.LocalTaskDataSource;
import com.momo.basedemo.dataSource.local.MyDatabase;
import com.momo.basedemo.dataSource.local.TaskDao;
import com.momo.basedemo.dataSource.remote.RemoteTaskDataSource;
import com.momo.basedemo.main.fragment.FragmentFirst;
import com.momo.basedemo.main.fragment.FragmentFourth;
import com.momo.basedemo.main.fragment.FragmentSecond;
import com.momo.basedemo.main.fragment.FragmentThird;
import com.momo.basedemo.thread.AppExecutors;

import java.util.ArrayList;
import java.util.List;


public class MainTabActivity extends BaseActivity {

    private ViewPager viewPager;
    private BottomNavigationView navigation;
    private MenuItem menuItem;

    private FragmentFirst fragment1;
    private FragmentSecond fragment2;
    private FragmentThird fragment3;
    private FragmentFourth fragment4;
    private List<Fragment> fragments;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;

                case R.id.navigation_dashboard: {
                    viewPager.setCurrentItem(1);

                    return true;
                }
                case R.id.navigation_notifications: {
                    viewPager.setCurrentItem(2);
                    return true;
                }
                case R.id.navigation_setting: {
                    viewPager.setCurrentItem(3);

                    return true;
                }

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBarHide(true);
        setContentView(R.layout.activity_main_tab);
        setTitle(null);
        initView();
        initFragment();

    }

    public void initView() {
        Injection.provideTasksRepository(mContext).getTasks(new TaskDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                System.out.println(tasks.size());
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
        viewPager = (ViewPager) findViewById(R.id.vp_content);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    navigation.getMenu().getItem(0).setChecked(false);
                }
                menuItem = navigation.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    private void initFragment() {
        fragment1 = FragmentFirst.newInstance();
        fragment2 = FragmentSecond.newInstance();
        fragment3 = FragmentThird.newInstance();
        fragment4 = FragmentFourth.newInstance();
        fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> list;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

    }

}
