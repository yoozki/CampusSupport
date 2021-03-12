package cn.yoozki.campussupport.user.pojo.vo;

import lombok.Data;

/**
 * @author yoozki
 * @date 2021/3/11
 */
@Data
public class UserAuthVO {

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 学校名字
     */
    private String campus;

    /**
     * 学号
     */
    private Long studentId;
}
