package cn.yoozki.campussupport.user.service.impl;

import cn.yoozki.campussupport.common.pojo.UserTokenDTO;
import cn.yoozki.campussupport.common.util.JwtUtils;
import cn.yoozki.campussupport.user.mapper.UserMapper;
import cn.yoozki.campussupport.user.pojo.UserDO;
import cn.yoozki.campussupport.user.pojo.dto.UserInfoDTO;
import cn.yoozki.campussupport.user.service.UserPassportService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

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
    public UserDO saveUser(UserInfoDTO userInfoDTO, String openId) {
        String avatar = userInfoDTO.getAvatar();
        String nickName = userInfoDTO.getNickName();
        UserDO userDO = new UserDO();
        userDO.setAvatar(avatar);
        userDO.setNickName(nickName);
        userDO.setStatus(UserDO.getNormalStatus());
        userDO.setOpenId(openId);
        userDO.setBalance(new BigDecimal("0.00"));
        Date date = new Date();
        userDO.setGmtCreate(date);
        userDO.setGmtModified(date);
        userMapper.insert(userDO);
        return userDO;
    }

    @Override
    public String generateToken(UserDO userDO) {
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setOpenId(userDO.getOpenId());
        userTokenDTO.setNickName(userDO.getNickName());
        userTokenDTO.setAvatar(userDO.getAvatar());
        userTokenDTO.setBalance(userDO.getBalance());
        try {
            String tokenMapJson = new ObjectMapper().writeValueAsString(userTokenDTO);
            return JwtUtils.createJWT(UUID.randomUUID().toString(), tokenMapJson);
        } catch (JsonProcessingException e) {
            return null;
        }
    }


}
