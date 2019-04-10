package com.momo.basedemo.net.schedulers;

import androidx.annotation.NonNull;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;

/**
 * @ProjectName: BaseDemo
 * @Package: com.momo.basedemo.net
 * @ClassName: BaseSchedulerProvider
 * @Description: java类作用描述
 * @Author: wangzhongli
 * @CreateDate: 2019/4/10 17:50
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/10 17:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    <T> ObservableTransformer<T, T> applySchedulers();
}
