package cn.yoozki.campussupport.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author yoozki
 * @date 2021/1/26
 */
@Data
@TableName("user")
public class UserDO {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String openId;
    private String nickName;
    private String avatar;
    private BigDecimal balance;
    private Integer status;
    private Date gmtCreate;
    private Date gmtModified;

}
