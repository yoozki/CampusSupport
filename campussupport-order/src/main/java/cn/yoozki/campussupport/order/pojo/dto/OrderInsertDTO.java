package cn.yoozki.campussupport.order.pojo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yoozki
 * @date 2021/2/10
 */
@Data
public class OrderInsertDTO {

    /**
     * 订单标题
     */
    private String orderTitle;

    /**
     * 订单描述
     */
    private String orderDetail;

    /**
     * 支付费用
     */
    private BigDecimal payCost;

    /**
     * 配送费用
     */
    private BigDecimal deliverCost;

    /**
     * 限定时间
     */
    private Date limitTime;

    /**
     * 标签id
     */
    private List<Integer> tagIdList;

}
