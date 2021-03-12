package cn.yoozki.campussupport.order.pojo.vo;

import cn.yoozki.campussupport.order.pojo.OrderTagDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 状态为待接单的订单
 * @author yoozki
 * @date 2021/2/11
 */
@Data
public class DemandOrderVO {

    /**
     * 目标(取件)地址
     */
    private String targetAddress;

    /**
     * 交付(送达)地址
     */
    private String deliveryAddress;

    /**
     * 订单标题
     */
    private String title;

    /**
     * 订单描述
     */
    private String detail;

    /**
     * 代付金额
     */
    private BigDecimal payCost;

    /**
     * 赏金
     */
    private BigDecimal reward;

    /**
     * 限定时间
     */
    private Date deliveryTime;

    /**
     * 性别限制(0:不限男女 1:只限男 2:只限女
     */
    private Integer sexLimit;

    /**
     *
     * 订单标签id
     */
    private Integer tagId;

}
