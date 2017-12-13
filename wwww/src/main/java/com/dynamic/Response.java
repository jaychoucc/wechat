package com.dynamic;

import com.google.common.collect.Maps;
import com.open.common.utils.DynamicObject;
import com.open.common.utils.ThrowableUtils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by LaoWang on 2017/5/4.
 */
public class Response implements Serializable {
    private static Map<String,String>messageMap = Maps.newHashMap();
    private String message;
    private boolean success = true;
    private int code;
    private String stackTrace;
    protected DynamicObject data = new DynamicObject();

    //初始化状态码与文字说明
    static {
        messageMap.put("0", "");

        messageMap.put("400", "HTTP 错误 400.0 - 访问被拒绝：错误的请求");
        messageMap.put("401", "HTTP 错误 401.1 - 未经授权：访问由于凭据无效被拒绝");
        messageMap.put("405", "用来访问本页面的 HTTP 谓词不被允许（方法不被允许）");
        messageMap.put("406", "客户端浏览器不接受所请求页面的 MIME 类型");
        messageMap.put("500", "HTTP 错误 500.0 - 服务器出错：内部服务器出错");

        messageMap.put("1005", "[服务器]运行时异常");
        messageMap.put("1006", "[服务器]空值异常");
        messageMap.put("1007", "[服务器]数据类型转换异常");
        messageMap.put("1008", "[服务器]IO异常");
        messageMap.put("1009", "[服务器]未知方法异常");
        messageMap.put("1010", "[服务器]数组越界异常");
        messageMap.put("1011", "[服务器]网络异常");
        messageMap.put("1012", "[短信验证码]发送频率太高");
        messageMap.put("1013", "[实名认证]认证频率太高");
        messageMap.put("1014", "[取消订单]取消订单频率太高");
    }

    public Response() {

    }

    public static Response success() {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    public static Response success(String message) {
        Response response = new Response();
        response.setSuccess(true);
        response.setMessage(message);
        return response;
    }

    public static Response success(String message, int code) {
        Response response = new Response();
        response.setSuccess(true);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public static Response error(int code) {
        Response response = new Response();
        response.setSuccess(true);
        response.setCode(code);
        response.setMessage(messageMap.get(String.valueOf(code)));
        return response;
    }

    public static Response fail(Throwable e) {
        Response response = fail(e.getMessage());
        response.setStackTrace(ThrowableUtils.toString(e));
        return response;
    }

    public static Response fail(String message) {
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(-1);
        response.setMessage(message);
        return response;
    }

    public static Response fail(String message, int code) {
        Response response = new Response();
        response.setSuccess(false);
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    public Response put(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Response putAll(Map<String, ?> map) {
        Iterator i$ = map.keySet().iterator();

        while(i$.hasNext()) {
            String key = (String)i$.next();
            Object value = map.get(key);
            if(value != null) {
                this.put(key, value);
            }
        }

        return this;
    }
    public boolean isEmpty() {
        return this.data == null || this.data.isEmpty();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public DynamicObject getData() {
        return data;
    }

    public void setData(DynamicObject data) {
        this.data = data;
    }
}
