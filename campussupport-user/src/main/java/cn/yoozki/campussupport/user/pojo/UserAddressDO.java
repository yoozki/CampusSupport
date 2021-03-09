package cn.yoozki.campussupport.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("user_address")
public class UserAddressDO {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer userId;
    private String name;
    private String phone;
    private String building;
    private String room;
    @TableField("is_default")
    private boolean defaultAddress;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
}
