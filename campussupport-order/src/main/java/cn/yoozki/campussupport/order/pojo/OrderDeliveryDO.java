package cn.yoozki.campussupport.order.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author yoozki
 * @date 2021/3/12
 */
@Data
@TableName("order_delivery")
public class OrderDeliveryDO {

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
     * 接单用户openId
     */
    private String deliveryOpenId;

    /**
     * 确认码
     */
    private Integer code;

    /**
     * 状态（ 1:已取消 2:正常
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
