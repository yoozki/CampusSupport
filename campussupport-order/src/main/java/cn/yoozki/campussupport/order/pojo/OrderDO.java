package cn.yoozki.campussupport.order.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;

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
     * 下单用户openId
     */
    private String receiverOpenId;

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
     * 代付款金额
     */
    private BigDecimal payCost;

    /**
     * 联系人姓名
     */
    private String receiverName;

    /**
     * 联系人手机
     */
    private String receiverPhone;

    /**
     * 赏金
     */
    private BigDecimal reward;

    /**
     * 限定时间
     */
    private Date deliveryTime;

    /**
     * 订单状态(0:已取消 1:待支付 2:待接单 3:已接单 4:已完成
     */
    private Integer status;

    /**
     * 性别限制(0:不限男女 1:只限男 2:只限女
     */
    private Integer sexLimit;

    /**
     * 订单标签id
     */
    private Integer tagId;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;
}
