package cn.yoozki.campussupport.common.enums;

/**
 * 错误码枚举类
 * @author yoozki
 * @date 2021/2/2
 */
public enum ErrorCodeEnum {

    /**
     * 错误码：10001 未知系统异常
     */
    UNKNOWN_ERROR(10000, "未知系统异常"),

    /**
     * 错误码：10110 Token失效
     */
    TOKEN_VERIFY_ERROR(10110, "Token已失效"),

    /**
     * 错误码：10202 账户已被冻结
     */
    USERNAME_IS_BLOCK(10202, "账户已被冻结"),

    /**
     * 错误码：10400 用户请求参数错误
     */
    USER_REQUEST_PARAM_ERROR(10400, "用户请求参数错误");



    private int code;
    private String msg;

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
