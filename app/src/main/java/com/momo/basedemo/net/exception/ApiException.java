package com.momo.basedemo.net.exception;

/**
 * @ProjectName: BaseDemo
 * @Package: com.momo.basedemo.net
 * @ClassName: ApiException
 * @Description: java类作用描述  服务端异常类
 * @Author: wangzhongli
 * @CreateDate: 2019/4/10 17:38
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/4/10 17:38
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ApiException extends Exception {

    private int code;
    private String displayMessage;

    public ApiException(int code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public ApiException(int code, String message, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
