package com.momo.basedemo.net.response;

/**
 * @ProjectName: BaseDemo
 * @Package: com.momo.basedemo.net
 * @ClassName: Response
 * @Description: java类作用描述
 * @Author: wangzhongli
 * @CreateDate: 2019/4/10 17:32
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/10 17:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Response<T> {

    private int code; // 返回的code
    private T data; // 具体的数据结果
    private String msg; // message 可用来返回接口的说明

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
