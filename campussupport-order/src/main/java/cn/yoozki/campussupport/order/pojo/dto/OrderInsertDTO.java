package cn.yoozki.campussupport.order.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yoozki
 * @date 2021/2/10
 */
@Data
public class OrderInsertDTO {

    /**
     * 目标(取件)地址
     */
    @NotBlank(message = "目标地址不能为空")
    private String targetAddress;

    /**
     * 交付(送达)地址
     */
    @NotBlank(message = "送达地址不能为空")
    private String deliveryAddress;

    /**
     * 订单标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 订单描述
     */
    @NotBlank(message = "细节不能为空")
    private String detail;

    /**
     * 代付款金额
     */
    @NotNull(message = "代付金额不能为空")
    @DecimalMin(value = "0", message = "代付金额必须大于0")
    private BigDecimal payCost;

    /**
     * 联系人姓名
     */
    @NotBlank(message = "联系人姓名不能为空")
    private String receiverName;

    /**
     * 联系人手机
     */
    @NotBlank(message = "联系人手机不能为空")
    private String receiverPhone;

    /**
     * 赏金
     */
    @NotNull(message = "赏金不能为空")
    @DecimalMin(value = "0", message = "赏金金额必须大于0")
    private BigDecimal reward;

    /**
     * 限定时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;

    /**
     * 性别限制(0:不限男女 1:只限男 2:只限女
     */
    @Range(min = 0, max = 2, message = "限制参数错误")
    private Integer sexLimit;

    /**
     * 订单标签id
     */
    @Range(min = 1, max = 3, message = "标签参数错误")
    private Integer tagId;

}
