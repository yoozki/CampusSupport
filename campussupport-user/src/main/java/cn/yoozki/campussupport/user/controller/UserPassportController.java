package cn.yoozki.campussupport.user.controller;

import cn.yoozki.campussupport.common.enums.RedisKeyEnum;
import cn.yoozki.campussupport.common.pojo.UserTokenDTO;
import cn.yoozki.campussupport.common.util.JwtUtils;
import cn.yoozki.campussupport.user.pojo.UserDO;
import cn.yoozki.campussupport.user.pojo.dto.UserInfoDTO;
import cn.yoozki.campussupport.user.pojo.dto.WechatDTO;
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

    private static String APP_SECRET = "6c8f9f4e00e206327c9fb22e0371c360";

    @Autowired
    private UserPassportService userPassportService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RestTemplate restTemplate;

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
            stringRedisTemplate.opsForValue().set(RedisKeyEnum.USER_TOKEN_KEY.getKeyName() + openId, token, 1, TimeUnit.DAYS);
            return JSONResult.ok(token);
        }
        String token = userPassportService.generateToken(userByOpenId);
        stringRedisTemplate.opsForValue().set(RedisKeyEnum.USER_TOKEN_KEY.getKeyName() + openId, token, 1, TimeUnit.DAYS);
        return JSONResult.ok(token);
    }

    @PostMapping("/verify/token")
    public JSONResult verifyToken(@RequestHeader("Authorization") String token) {
        if (StringUtils.isBlank(token)) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getMsg());
        }
        UserTokenDTO userTokenDTO = null;
        try {
            userTokenDTO = JwtUtils.parseSubject(token);
        } catch (Exception e) {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
        String openId = userTokenDTO.getOpenId();
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey(RedisKeyEnum.USER_TOKEN_KEY.getKeyName() + openId))) {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
        return JSONResult.ok();
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
