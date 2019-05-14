package com.momo.basedemo.block;

import com.momo.basedemo.net.response.ResponseTransformer;
import com.momo.basedemo.net.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;



public class Presenter {

    private Model model;

    private Contract.View view;

    private BaseSchedulerProvider schedulerProvider;

    private CompositeDisposable mDisposable;

    public Presenter(Model model,
                     Contract.View view,
                     BaseSchedulerProvider schedulerProvider) {
        this.model = model;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        mDisposable = new CompositeDisposable();

    }

    public void despose(){
        mDisposable.dispose();
    }

    public void getList() {

        Disposable disposable = model.getCarList("xxxxxx")
                .compose(ResponseTransformer.handleResult())
                .compose(schedulerProvider.applySchedulers())
                .subscribe(carBeans -> {
                    // 处理数据 直接获取到List<JavaBean> carBeans
                    view.getDataSuccess();
                }, throwable -> {
                    // 处理异常
                    view.getDataFail();
                });

        mDisposable.add(disposable);
    }

}
