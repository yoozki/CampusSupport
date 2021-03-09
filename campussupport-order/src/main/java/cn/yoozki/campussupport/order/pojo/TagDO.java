package cn.yoozki.campussupport.order.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author yoozki
 * @date 2021/2/11
 */
@Data
@TableName("tag")
public class TagDO {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 标签名
     */
    private String tagName;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

}
