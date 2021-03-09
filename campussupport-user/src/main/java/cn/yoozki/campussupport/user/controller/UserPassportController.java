package cn.yoozki.campussupport.user.controller;

import cn.yoozki.campussupport.common.enums.RedisKeyEnum;
import cn.yoozki.campussupport.user.pojo.UserDO;
import cn.yoozki.campussupport.user.pojo.dto.UserInfoDTO;
import cn.yoozki.campussupport.user.pojo.dto.WechatDTO;
import cn.yoozki.campussupport.user.service.UserPassportService;
import cn.yoozki.campussupport.common.enums.ErrorCodeEnum;
import cn.yoozki.campussupport.common.pojo.UserTokenDTO;
import cn.yoozki.campussupport.common.util.JSONResult;
import cn.yoozki.campussupport.common.util.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
    public JSONResult login(@RequestBody UserInfoDTO userInfoDTO) throws JsonProcessingException {
        String code = userInfoDTO.getCode();
        if (StringUtils.isBlank(code)) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getMsg());
        }
        String requestUrl = WECHAT_OPENID_API + "?appid=" + APP_ID + "&secret=" + APP_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        ResponseEntity<String> result = restTemplate.getForEntity(requestUrl, String.class);
        String body = result.getBody();
        WechatDTO wechatDTO = new ObjectMapper().readValue(body, WechatDTO.class);
        String openId = wechatDTO.getOpenid();
        UserDO userByOpenId = userPassportService.getUserByOpenId(openId);
        if (userByOpenId == null) {
            UserDO userDO = generateUserDO(userInfoDTO, openId);
            userPassportService.saveUser(userDO);
            String token = generateToken(userDO);
            stringRedisTemplate.opsForValue().set(RedisKeyEnum.USER_TOKEN_KEY.getKeyName() + token, openId, 1, TimeUnit.HOURS);
            return JSONResult.ok(token);
        }
        String token = generateToken(userByOpenId);
        stringRedisTemplate.opsForValue().set(RedisKeyEnum.USER_TOKEN_KEY.getKeyName() + token, openId, 1, TimeUnit.HOURS);
        return JSONResult.ok(token);
    }

    @PostMapping("/token/verify")
    public JSONResult verifyToken(@RequestHeader String Authorization) {
        if (!stringRedisTemplate.hasKey(RedisKeyEnum.USER_TOKEN_KEY.getKeyName() + Authorization)) {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
        return JSONResult.ok();
    }

    private UserDO generateUserDO(UserInfoDTO userInfoDTO, String openId) {
        String avatar = userInfoDTO.getAvatar();
        String nickName = userInfoDTO.getNickName();
        UserDO userDO = new UserDO();
        userDO.setAvatar(avatar);
        userDO.setNickName(nickName);
        userDO.setStatus(UserDO.getNormalStatus());
        userDO.setOpenId(openId);
        userDO.setBalance(new BigDecimal("0.00"));
        Date date = new Date();
        userDO.setGmtCreate(date);
        userDO.setGmtModified(date);
        return userDO;
    }

    private String generateToken(UserDO userDO) {
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setOpenId(userDO.getOpenId());
        userTokenDTO.setNickName(userDO.getNickName());
        userTokenDTO.setAvatar(userDO.getAvatar());
        userTokenDTO.setBalance(userDO.getBalance());
        try {
            String tokenMapJson = new ObjectMapper().writeValueAsString(userTokenDTO);
            return JwtUtils.createJWT(UUID.randomUUID().toString(), tokenMapJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //    @GetMapping("/token")
//    public JSONResult login(@RequestParam String username, @RequestParam String password) {
//        if (StringUtils.isBlank(username)) {
//            return JSONResult.errorMsg(ErrorCodeEnum.USERNAME_CHECK_ERROR.getCode(), ErrorCodeEnum.USERNAME_CHECK_ERROR.getMsg());
//        }
//        if (StringUtils.isBlank(password)) {
//            return JSONResult.errorMsg(ErrorCodeEnum.PASSWORD_CHECK_ERROR.getCode(), ErrorCodeEnum.PASSWORD_CHECK_ERROR.getMsg());
//        }
//        UserDO userResult = userPassportService.getUserByUsername(username);
//        if (userResult == null) {
//            return JSONResult.errorMsg(ErrorCodeEnum.USERNAME_NOT_EXIST_ERROR.getCode(), ErrorCodeEnum.USERNAME_NOT_EXIST_ERROR.getMsg());
//        }
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        if (!passwordEncoder.matches(password, userResult.getPassword())) {
//            return JSONResult.errorMsg(ErrorCodeEnum.USERNAME_PASSWORD_LOGIN_ERROR.getCode(), ErrorCodeEnum.USERNAME_PASSWORD_LOGIN_ERROR.getMsg());
//        }
//        String token = generateToken(userResult);
//        stringRedisTemplate.opsForValue().set(token, "token", 1, TimeUnit.HOURS);
//        return JSONResult.ok(token);
//    }
}
