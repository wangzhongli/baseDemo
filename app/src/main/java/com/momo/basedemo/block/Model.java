package com.momo.basedemo.block;


import com.momo.basedemo.bean.JavaBean;
import com.momo.basedemo.net.NetWorkManager;
import com.momo.basedemo.net.response.Response;

import java.util.List;

import io.reactivex.Observable;

/**
 */

public class Model implements Contract.Model {
    @Override
    public Observable<Response<List<JavaBean>>> getCarList(String userId) {
        return NetWorkManager.getRequest().getList(userId);
    }
}
