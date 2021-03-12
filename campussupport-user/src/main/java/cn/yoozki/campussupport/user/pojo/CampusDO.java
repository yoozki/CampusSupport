package cn.yoozki.campussupport.user.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yoozki
 * @date 2021/3/11
 */
@Data
@TableName("campus")
public class CampusDO {


    private Long id;

    private String campusName;

}
