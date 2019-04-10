package com.momo.basedemo.net.request;

import com.momo.basedemo.bean.JavaBean;
import com.momo.basedemo.net.response.Response;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @ProjectName: BaseDemo
 * @Package: com.momo.basedemo.net
 * @ClassName: Request
 * @Description: java类作用描述
 * @Author: wangzhongli
 * @CreateDate: 2019/4/10 17:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/10 17:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public interface Request {
    // 填上需要访问的服务器地址
    String HOST = "https://www.xxx.com/app_v5/";

    @POST("?service=sser.getList")
    Observable<Response<List<JavaBean>>> getList(@Query("id") String id);
}
