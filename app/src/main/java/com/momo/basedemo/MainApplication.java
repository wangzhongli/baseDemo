package com.momo.basedemo;

import android.app.Application;

import com.momo.basedemo.net.NetWorkManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * @ProjectName: BaseDemo
 * @Package: com.momo.basedemo
 * @ClassName: MainApplication
 * @Description: java类作用描述  主Application
 * @Author: wangzhongli
 * @CreateDate: 2019/4/10 16:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/10 16:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance().init();
        if (BuildConfig.DEBUG) {
            Logger.addLogAdapter(new AndroidLogAdapter());
        }
    }


}
