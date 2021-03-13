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
     * 错误码：10140 用户学校认证失败
     */
    USER_AUTH_ERROR(10140, "用户学校认证失败"),

    /**
     * 错误码：10141 用户认证信息已被使用
     */
    USER_AUTH_USED_ERROR(10141, "用户认证信息已被使用"),

    /**
     *
     */
    USER_AUTH_NOT_EXIST(10141, "用户未认证"),

    /**
     * 错误码：10202 账户已被冻结
     */
    USERNAME_IS_BLOCK(10202, "账户已被冻结"),

    /**
     * 错误码：10300 Token无效
     */
    TOKEN_VERIFY_ERROR(10300, "Token无效"),

    /**
     * 错误码：10400 用户请求参数错误
     */
    USER_REQUEST_PARAM_ERROR(10400, "用户请求参数错误"),

    /**
     * 错误码：10410 订单状态错误
     */
    ORDER_STATUS_ERROR(10410, "订单状态错误"),

    /**
     *
     */
    ORDER_CODE_ERROR(10420, "订单确认码错误"),

    /**
     * 错误码：10601 用户余额不足
     */
    USER_BALANCE_INSUFFICIENT(10601, "用户余额不足");

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
