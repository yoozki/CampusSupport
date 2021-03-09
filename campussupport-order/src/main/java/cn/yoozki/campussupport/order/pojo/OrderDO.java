package cn.yoozki.campussupport.order.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单DO
 * @author yoozki
 * @date 2021/2/10
 */
@Data
@TableName("order_info")
public class OrderDO {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 下单用户id
     */
    private String receiveUserId;

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
     * 订单状态(0:已取消 1:待接单 2:正在处理 3:已完成
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;
}
