package cn.yoozki.campussupport.user.service.impl;

import cn.yoozki.campussupport.user.mapper.UserMapper;
import cn.yoozki.campussupport.user.pojo.UserDO;
import cn.yoozki.campussupport.user.pojo.vo.UserInfoVO;
import cn.yoozki.campussupport.user.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yoozki
 * @date 2021/3/8
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfoVO getUserInfo(String openId) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openId);
        UserDO userDO = userMapper.selectOne(wrapper);
        if (userDO == null) {
            return null;
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setNickName(userDO.getNickName());
        userInfoVO.setBalance(userDO.getBalance());
        userInfoVO.setAvatar(userDO.getAvatar());
        return userInfoVO;
    }

    @Override
    public void updateUserInfo(UserDO userDO) {
        userMapper.updateById(userDO);
    }
}
