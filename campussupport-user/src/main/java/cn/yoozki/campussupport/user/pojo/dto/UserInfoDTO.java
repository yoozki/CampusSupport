package cn.yoozki.campussupport.user.pojo.dto;

import lombok.Data;

/**
 * @author yoozki
 * @date 2021/3/8
 */
@Data
public class UserInfoDTO {

    /**
     * code
     */
    private String code;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickName;
}
