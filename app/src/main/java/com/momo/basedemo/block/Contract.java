package com.momo.basedemo.block;


import com.momo.basedemo.bean.JavaBean;
import com.momo.basedemo.net.response.Response;

import java.util.List;

import io.reactivex.Observable;




public class Contract {

    public interface Persenter {
        public void getCarList(String userId);
    }

    public interface View {
        void getDataSuccess();

        void getDataFail();
    }

    public interface Model {
        Observable<Response<List<JavaBean>>> getCarList(String userId);
    }

}
