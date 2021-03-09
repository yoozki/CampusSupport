package cn.yoozki.campussupport.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author yoozki
 * @date 2021/1/26
 */
@Data
@TableName("user_profiles")
public class UserProfilesDO {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer userId;
    private String sign;
    private String intro;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
