package com.momo.basedemo.net;

import com.momo.basedemo.net.request.Request;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @ProjectName: BaseDemo
 * @Package: com.momo.basedemo.net
 * @ClassName: NetWorkManager
 * @Description: java类作用描述 retrofit 的封装类
 * @Author: wangzhongli
 * @CreateDate: 2019/4/10 17:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/10 17:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NetWorkManager {

    private static volatile NetWorkManager mInstance = null;

    private static Retrofit retrofit;

    private static volatile Request request = null;

    private NetWorkManager() {
    }

    public static NetWorkManager getInstance() {
        if (null == mInstance) {
            synchronized (NetWorkManager.class) {
                if (null == mInstance) {
                    mInstance = new NetWorkManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化必要对象和参数
     */
    public void init() {
        // 初始化okhttp
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        // 初始化 retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(Request.HOST)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Request getRequest() {
        if (request == null) {
            synchronized (Request.class) {
                request = retrofit.create(Request.class);
            }
        }
        return request;
    }
}
