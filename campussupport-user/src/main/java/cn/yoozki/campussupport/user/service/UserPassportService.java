package cn.yoozki.campussupport.user.service;

import cn.yoozki.campussupport.user.pojo.UserDO;

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
     * @param user
     * @return
     */
    UserDO saveUser(UserDO user);

}
