package cn.yoozki.campussupport.user.service;

import cn.yoozki.campussupport.user.pojo.CampusDO;

import java.util.List;

/**
 * @author yoozki
 * @date 2021/3/11
 */
public interface UserPublicService {

    /**
     * 获取所有大学名字
     * @return
     */
    List<CampusDO> listCampusDO();

}
