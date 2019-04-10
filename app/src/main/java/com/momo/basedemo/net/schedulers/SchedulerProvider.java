package com.momo.basedemo.net.schedulers;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;

/**
 * @ProjectName: BaseDemo
 * @Package: com.momo.basedemo.net
 * @ClassName: SchedulerProvider
 * @Description: java类作用描述
 * @Author: wangzhongli
 * @CreateDate: 2019/4/10 17:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/10 17:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SchedulerProvider implements BaseSchedulerProvider {

    @Nullable
    private static SchedulerProvider INSTANCE;

    // Prevent direct instantiation.
    private SchedulerProvider() {
    }

    public static synchronized SchedulerProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchedulerProvider();
        }
        return INSTANCE;
    }

    @Override
    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @NonNull
    @Override
    public <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(io())
                .observeOn(ui());
    }
}
