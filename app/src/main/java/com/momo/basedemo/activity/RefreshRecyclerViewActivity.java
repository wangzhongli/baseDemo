package com.momo.basedemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.momo.basedemo.R;
import com.momo.basedemo.activity.test.Request;
import com.momo.basedemo.base.BaseActivity;
import com.momo.basedemo.loadmore.CustomLoadMoreView;
import com.momo.basedemo.widget.decoration.GridItemDecoration;
import com.momo.basedemo.activity.test.RequestCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefreshRecyclerViewActivity extends BaseActivity {

    private static final int PAGE_SIZE = 10;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    List<String> datas = new ArrayList<>();

    int mNextRequestPage;
    private BaseQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBackBtn();
        setTitle("RecyclerView Pull TO Refresh");
        setContentView(R.layout.activity_refresh_recyclerview);
        ButterKnife.bind(this);
        initView();

    }

    private void initRefreshLayout() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }


    /**
     * 初始化view
     */
    private void initView() {

        for (int i = 0; i < 1000; i++) {
            datas.add("测试RecyclerView性能" + i);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.addItemDecoration(new GridItemDecoration(mContext, R.color.colorAccent));
        initAdapter();
        addHeadView();
        initRefreshLayout();
        swipeLayout.setRefreshing(true);
        refresh();
    }

    private void initAdapter() {
        adapter = new Myadapter(datas);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, recyclerView);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        mAdapter.setPreLoadNumber(3);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                Toast.makeText(mContext, datas.get(position), Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        mNextRequestPage = 1;
        adapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        //TODO 这里模拟处理刷新请求数据操作
        new Request(mNextRequestPage, new RequestCallBack() {
            @Override
            public void success(List<String> data) {
                setData(true, data);
                adapter.setEnableLoadMore(true);
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void fail(Exception e) {
                Toast.makeText(mContext, "网络错误！", Toast.LENGTH_LONG).show();
                adapter.setEnableLoadMore(true);
                swipeLayout.setRefreshing(false);
            }
        }).start();
    }

    private void addHeadView() {
        View headView = getLayoutInflater().inflate(R.layout.head_view, (ViewGroup) recyclerView.getParent(), false);
//        headView.findViewById(R.id.iv).setVisibility(View.GONE);
        ((TextView) headView.findViewById(R.id.tv)).setText("change load view");
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setNewData(null);
                adapter.setLoadMoreView(new CustomLoadMoreView());
                recyclerView.setAdapter(adapter);
                Toast.makeText(mContext, "change complete", Toast.LENGTH_LONG).show();

                swipeLayout.setRefreshing(true);
                refresh();
            }
        });
        adapter.addHeaderView(headView);
    }


    public static class Myadapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public Myadapter(@Nullable List data) {
            super(R.layout.recyclerview_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.item_tv, (CharSequence) item);
        }
    }

    private void loadMore() {
        new Request(mNextRequestPage, new RequestCallBack() {
            @Override
            public void success(List<String> data) {
                boolean isRefresh = mNextRequestPage == 1;
                setData(isRefresh, data);
            }

            @Override
            public void fail(Exception e) {
                adapter.loadMoreFail();
                Toast.makeText(mContext, "网络错误", Toast.LENGTH_LONG).show();
            }
        }).start();
    }

    private void setData(boolean isRefresh, List data) {
        mNextRequestPage++;
        final int size = data == null ? 0 : data.size();
        if (isRefresh) {
            adapter.setNewData(data);
        } else {
            if (size > 0) {
                adapter.addData(data);
            }
        }
        if (size < PAGE_SIZE) {
            //第一页如果不够一页就不显示没有更多数据布局
            adapter.loadMoreEnd(isRefresh);
            Toast.makeText(this, "no more data", Toast.LENGTH_SHORT).show();
        } else {
            adapter.loadMoreComplete();
        }
    }
}
