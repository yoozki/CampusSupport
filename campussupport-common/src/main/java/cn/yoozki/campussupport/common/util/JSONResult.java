package cn.yoozki.campussupport.common.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 521：用户名不存在
 * 522：密码错误
 * @author yoozki
 * @date 2021/1/27
 */
public class JSONResult {
    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private Object data;

    @JsonIgnore
    private String ok;

    public static JSONResult ok(Object data) {
        return new JSONResult(data);
    }

    public static JSONResult ok() {
        return new JSONResult(null);
    }

    public static JSONResult errorMsg(int code, String msg) {
        return new JSONResult(code, msg, null);
    }

    private JSONResult() {

    }

    private JSONResult(Integer status, String msg, Object data) {
        this.code = status;
        this.msg = msg;
        this.data = data;
    }

    private JSONResult(Integer status, String msg, Object data, String ok) {
        this.code = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }

    private JSONResult(Object data) {
        this.code = 00000;
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.code == 00000;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer status) {
        this.code = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }
}
