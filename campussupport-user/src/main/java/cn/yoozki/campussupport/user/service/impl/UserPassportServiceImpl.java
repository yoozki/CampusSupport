package cn.yoozki.campussupport.user.service.impl;

import cn.yoozki.campussupport.user.mapper.UserMapper;
import cn.yoozki.campussupport.user.pojo.UserDO;
import cn.yoozki.campussupport.user.service.UserPassportService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author yoozki
 * @date 2021/2/2
 */
@Service
public class UserPassportServiceImpl implements UserPassportService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDO getUserByOpenId(String openId) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openId);
        return userMapper.selectOne(wrapper);
    }


    @Override
    public UserDO saveUser(UserDO user) {
        userMapper.insert(user);
        return user;
    }
}
