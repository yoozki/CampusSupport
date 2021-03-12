package cn.yoozki.campussupport.user.service;

import cn.yoozki.campussupport.user.pojo.UserAuthDO;
import cn.yoozki.campussupport.user.pojo.dto.UserAuthDTO;
import cn.yoozki.campussupport.user.pojo.vo.UserAuthVO;

/**
 * @author yoozki
 * @date 2021/3/11
 */
public interface UserAuthService {

    /**
     * 向 user_auth 表中插入数据
     * @param userAuthDTO
     * @param openId
     */
    UserAuthDO insertUserAuth(UserAuthDTO userAuthDTO, String openId);

    /**
     * 根据 openId 获取用户认证信息
     * @param openId
     * @return
     */
    UserAuthVO getUserAuth(String openId);
}
