package cn.yoozki.campussupport.user.service;

import cn.yoozki.campussupport.user.pojo.vo.UserInfoVO;

/**
 * @author yoozki
 * @date 2021/3/8
 */
public interface UserInfoService {

    /**
     * 根据openId获取用户信息
     * @param openId
     * @return
     */
    UserInfoVO getUserInfo(String openId);

}
