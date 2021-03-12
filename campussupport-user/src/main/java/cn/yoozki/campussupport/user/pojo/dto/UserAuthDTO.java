package cn.yoozki.campussupport.user.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yoozki
 * @date 2021/3/11
 */
@Data
public class UserAuthDTO {

    /**
     * 学校Id
     */
    @NotNull
    private Long campusId;

    /**
     * 真实姓名
     */
    @NotBlank
    private String realName;


    /**
     * 学号
     */
    @NotNull
    private Long studentId;

}
