package cn.yoozki.campussupport.order.common;

import lombok.Getter;

/**
 * @author yoozki
 * @date 2021/3/13
 */
public class OrderConst {

    /**
     * 已取消状态码
     */
    public static final Integer CANCEL_STATUS = 0;

    /**
     * 待支付状态码
     */
    public static final Integer UNPAID_STATUS = 1;

    /**
     * 待接单状态码
     */
    public static final Integer WAIT_STATUS = 2;

    /**
     * 正在进行状态码
     */
    public static final Integer PROCESS_STATUS = 3;

    /**
     * 已完成状态码
     */
    public static final Integer COMPLETE_STATUS = 4;

}
