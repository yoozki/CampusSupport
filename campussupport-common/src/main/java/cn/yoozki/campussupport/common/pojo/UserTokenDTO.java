package cn.yoozki.campussupport.common.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yoozki
 * @date 2021/2/10
 */
@Data
public class UserTokenDTO {

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 余额
     */
    private BigDecimal balance;
}
