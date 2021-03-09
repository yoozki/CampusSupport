package cn.yoozki.campussupport.user.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author yoozki
 * @date 2021/3/8
 */
@Data
public class WechatDTO {

    private String openid;

    @JsonProperty("session_key")
    private String sessionKey;
}
