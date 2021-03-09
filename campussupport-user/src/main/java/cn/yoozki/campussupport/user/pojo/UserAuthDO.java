package cn.yoozki.campussupport.user.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author yoozki
 * @date 2021/2/11
 */
@Data
@TableName("user_auth")
public class UserAuthDO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户名
     */
    private String userId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 学号
     */
    private Long studentId;

    /**
     * 学校名
     */
    private String campus;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


}
