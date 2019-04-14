package com.momo.basedemo.activity.test;

import java.util.List;

public interface RequestCallBack {
    void success(List<String> data);

    void fail(Exception e);
}
