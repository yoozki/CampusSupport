package cn.yoozki.campussupport.user.service.impl;

import cn.yoozki.campussupport.user.mapper.CampusMapper;
import cn.yoozki.campussupport.user.mapper.UserMapper;
import cn.yoozki.campussupport.user.pojo.CampusDO;
import cn.yoozki.campussupport.user.service.UserPublicService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yoozki
 * @date 2021/3/11
 */
@Service
public class UserPublicServiceImpl implements UserPublicService {

    @Autowired
    private CampusMapper campusMapper;

    @Override
    public List<CampusDO> listCampusDO() {
        QueryWrapper<CampusDO> wrapper = new QueryWrapper<>();
        return campusMapper.selectList(wrapper);
    }
}
