package com.momo.basedemo.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.momo.basedemo.R;
import com.momo.basedemo.activity.RecyclerViewActivity;
import com.momo.basedemo.activity.RefreshRecyclerViewActivity;
import com.momo.basedemo.base.BaseFragment;
import com.momo.basedemo.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FragmentFirst extends BaseFragment {

    Unbinder unbinder;

    public static FragmentFirst newInstance() {

        Bundle args = new Bundle();

        FragmentFirst fragment = new FragmentFirst();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.main,R.id.item_tv, R.id.item_tv1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main:
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
                case R.id.item_tv:
                startActivity(new Intent(getActivity(), RecyclerViewActivity.class));
                break;
            case R.id.item_tv1:
                startActivity(new Intent(getActivity(), RefreshRecyclerViewActivity.class));
                break;
        }
    }
}
