package cn.yoozki.campussupport.user.controller;

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
    public JSONResult getUserInfo(@RequestHeader String Authorization) {
        UserInfoVO userInfoVO = null;
        try {
            UserTokenDTO userTokenDTO = JwtUtils.parseSubject(Authorization);
            String openId = userTokenDTO.getOpenId();

            String nickName = userTokenDTO.getNickName();
            userInfoVO = new UserInfoVO();
//            userInfoVO.setAvatar(avatar);
//            userInfoVO.setBalance(balance);
            userInfoVO.setNickName(nickName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return JSONResult.ok(userInfoVO);
    }

}
