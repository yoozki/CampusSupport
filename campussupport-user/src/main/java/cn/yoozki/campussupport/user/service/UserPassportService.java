package cn.yoozki.campussupport.user.service;

import cn.yoozki.campussupport.user.pojo.UserDO;
import cn.yoozki.campussupport.user.pojo.dto.UserInfoDTO;

/**
 * @author yoozki
 * @date 2021/2/2
 */
public interface UserPassportService {

    /**
     * 根据微信openId查找对应用户
     * @param username
     * @return
     */
    UserDO getUserByOpenId(String username);

    /**
     * 插入用户
     * @param userInfoDTO
     * @param openId
     * @return userDO
     */
    UserDO saveUser(UserInfoDTO userInfoDTO, String openId);

    /**
     * 生成token
     * @param userDO
     * @return token
     */
    String generateToken(UserDO userDO);

}
