package cn.yoozki.campussupport.user.controller;

import cn.yoozki.campussupport.common.enums.RedisKeyEnum;
import cn.yoozki.campussupport.common.pojo.UserTokenDTO;
import cn.yoozki.campussupport.common.util.JwtUtils;
import cn.yoozki.campussupport.user.pojo.UserDO;
import cn.yoozki.campussupport.user.pojo.dto.UserInfoDTO;
import cn.yoozki.campussupport.user.pojo.dto.WechatDTO;
import cn.yoozki.campussupport.user.service.UserInfoService;
import cn.yoozki.campussupport.user.service.UserPassportService;
import cn.yoozki.campussupport.common.enums.ErrorCodeEnum;
import cn.yoozki.campussupport.common.util.JSONResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.TimeUnit;

/**
 * @author yoozki
 * @date 2021/1/26
 */
@RestController
@RequestMapping("/passport")
public class UserPassportController {

    private static String WECHAT_OPENID_API = "https://api.weixin.qq.com/sns/jscode2session";

    private static String APP_ID = "wx624d6613af2b296f";

    private static String APP_SECRET = "95f2059b264d0a4ef91682bc55ff4f1b";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserPassportService userPassportService;

    @Autowired
    private UserInfoService userInfoService;


    @PostMapping("/token")
    public JSONResult login(@RequestBody UserInfoDTO userInfoDTO) {
        String code = userInfoDTO.getCode();
        if (StringUtils.isBlank(code)) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getMsg());
        }
        String openId = getOpenId(code);
        if (openId == null) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getMsg());
        }
        UserDO userByOpenId = userPassportService.getUserByOpenId(openId);
        if (userByOpenId == null) {
            UserDO userDO = userPassportService.saveUser(userInfoDTO, openId);
            String token = userPassportService.generateToken(userDO);
            return JSONResult.ok(token);
        }
        String avatar = userInfoDTO.getAvatar();
        String nickName = userInfoDTO.getNickName();
        userByOpenId.setAvatar(avatar);
        userByOpenId.setNickName(nickName);
        userInfoService.updateUserInfo(userByOpenId);
        String token = userPassportService.generateToken(userByOpenId);
        System.out.println(token);
        return JSONResult.ok(token);
    }

    private String getOpenId(String code) {
        String requestUrl = WECHAT_OPENID_API + "?appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        ResponseEntity<String> result = restTemplate.getForEntity(requestUrl, String.class);
        String body = result.getBody();
        WechatDTO wechatDTO = null;
        try {
            wechatDTO = new ObjectMapper().readValue(body, WechatDTO.class);
        } catch (JsonProcessingException e) {
            return null;
        }
        return wechatDTO.getOpenid();
    }

}
