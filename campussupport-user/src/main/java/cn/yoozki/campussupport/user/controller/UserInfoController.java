package cn.yoozki.campussupport.user.controller;

import cn.yoozki.campussupport.common.enums.ErrorCodeEnum;
import cn.yoozki.campussupport.common.pojo.UserTokenDTO;
import cn.yoozki.campussupport.common.util.JSONResult;
import cn.yoozki.campussupport.common.util.JwtUtils;
import cn.yoozki.campussupport.user.pojo.vo.UserInfoVO;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


/**
 * @author yoozki
 * @date 2021/3/8
 */
@RestController
@RequestMapping("/info")
public class UserInfoController {

    @GetMapping()
    public JSONResult getUserInfo(@RequestHeader("Authorization") String token) {
        UserTokenDTO userTokenDTO = JwtUtils.parseSubject(token);
        if (userTokenDTO != null) {
            UserInfoVO userInfoVO = new UserInfoVO();
            String avatar = userTokenDTO.getAvatar();
            String nickName = userTokenDTO.getNickName();
            BigDecimal balance = userTokenDTO.getBalance();
            userInfoVO = new UserInfoVO();
            userInfoVO.setAvatar(avatar);
            userInfoVO.setBalance(balance);
            userInfoVO.setNickName(nickName);
            return JSONResult.ok(userInfoVO);
        } else {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
    }

}
