package cn.yoozki.campussupport.user.controller;

import cn.yoozki.campussupport.common.enums.ErrorCodeEnum;
import cn.yoozki.campussupport.common.pojo.UserTokenDTO;
import cn.yoozki.campussupport.common.util.JSONResult;
import cn.yoozki.campussupport.common.util.JwtUtils;
import cn.yoozki.campussupport.user.pojo.UserAuthDO;
import cn.yoozki.campussupport.user.pojo.dto.UserAuthDTO;
import cn.yoozki.campussupport.user.pojo.vo.UserAuthVO;
import cn.yoozki.campussupport.user.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author yoozki
 * @date 2021/2/11
 */
@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @GetMapping
    public JSONResult getUserAuth(@RequestHeader("Authorization") String token) {
        UserTokenDTO userTokenDTO = JwtUtils.parseSubject(token);
        if (userTokenDTO == null) {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
        String openId = userTokenDTO.getOpenId();
        UserAuthVO userAuthVO = userAuthService.getUserAuth(openId);
        if (userAuthVO == null) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_AUTH_NOT_EXIST.getCode(), ErrorCodeEnum.USER_AUTH_NOT_EXIST.getMsg());
        }
        return JSONResult.ok(userAuthVO);
    }


    @PostMapping
    public JSONResult insertUserAuth(@RequestHeader("Authorization") String token, @Validated @RequestBody UserAuthDTO userAuthDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getMsg(), bindingResult.getAllErrors());
        }
        UserTokenDTO userTokenDTO = JwtUtils.parseSubject(token);
        if (userTokenDTO == null) {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
        String openId = userTokenDTO.getOpenId();
        if (userAuthService.getUserAuth(openId) != null) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_AUTH_USED_ERROR.getCode(), ErrorCodeEnum.USER_AUTH_USED_ERROR.getMsg());
        }
        UserAuthDO userAuthDO = userAuthService.insertUserAuth(userAuthDTO, openId);
        if (userAuthDO == null) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_AUTH_ERROR.getCode(), ErrorCodeEnum.USER_AUTH_ERROR.getMsg());
        }
        return JSONResult.ok();
    }

}
