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
public class RequireOrderVO {

    /**
     * 下单用户昵称
     */
    private String receiveUserNickname;

    /**
     * 下单用户头像
     */
    private String receiveUserAvatar;

    /**
     * 订单标题
     */
    private String orderTitle;

    /**
     * 订单描述
     */
    private String orderDetail;

    /**
     * 付款金额
     */
    private BigDecimal payCost;

    /**
     * 配送金额
     */
    private BigDecimal deliverCost;

    /**
     * 限定时间
     */
    private Date limitTime;

    /**
     * 标签名集合
     */
    private List<OrderTagVO> orderTagVOList;

}
