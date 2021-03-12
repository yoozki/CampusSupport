package cn.yoozki.campussupport.user.service.impl;

import cn.yoozki.campussupport.user.mapper.CampusMapper;
import cn.yoozki.campussupport.user.mapper.UserAuthMapper;
import cn.yoozki.campussupport.user.pojo.CampusDO;
import cn.yoozki.campussupport.user.pojo.UserAuthDO;
import cn.yoozki.campussupport.user.pojo.dto.UserAuthDTO;
import cn.yoozki.campussupport.user.pojo.vo.UserAuthVO;
import cn.yoozki.campussupport.user.service.UserAuthService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author yoozki
 * @date 2021/3/11
 */
@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private CampusMapper campusMapper;


    @Override
    public UserAuthDO insertUserAuth(UserAuthDTO userAuthDTO, String openId) {
        Long campusId = userAuthDTO.getCampusId();
        CampusDO campusDO = campusMapper.selectById(campusId);
        if (campusDO == null) {
            return null;
        }
        String realName = userAuthDTO.getRealName();
        Long studentId = userAuthDTO.getStudentId();
        String campusName = campusDO.getCampusName();
        UserAuthDO userAuthDO = new UserAuthDO();
        userAuthDO.setOpenId(openId);
        userAuthDO.setCampus(campusName);
        userAuthDO.setRealName(realName);
        userAuthDO.setStudentId(studentId);
        Date date = new Date();
        userAuthDO.setGmtCreate(date);
        userAuthDO.setGmtModified(date);
        userAuthMapper.insert(userAuthDO);
        return userAuthDO;
    }

    @Override
    public UserAuthVO getUserAuth(String openId) {
        QueryWrapper<UserAuthDO> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id", openId);
        UserAuthDO userAuthDO = userAuthMapper.selectOne(wrapper);
        if (userAuthDO == null) {
            return null;
        }
        UserAuthVO userAuthVO = new UserAuthVO();
        String realName = userAuthDO.getRealName();
        String campus = userAuthDO.getCampus();
        Long studentId = userAuthDO.getStudentId();
        userAuthVO.setCampus(campus);
        userAuthVO.setRealName(realName);
        userAuthVO.setStudentId(studentId);
        return userAuthVO;
    }
}
