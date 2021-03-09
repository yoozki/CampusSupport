package cn.yoozki.campussupport.user.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yoozki
 * @date 2021/3/8
 */
@Data
public class UserInfoVO {

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
