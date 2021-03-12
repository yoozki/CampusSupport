package cn.yoozki.campussupport.user.service.impl;

import cn.yoozki.campussupport.user.api.UserBalanceService;
import cn.yoozki.campussupport.user.mapper.UserMapper;
import cn.yoozki.campussupport.user.pojo.UserDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author yoozki
 * @date 2021/3/10
 */
@DubboService
public class UserBalanceServiceImpl implements UserBalanceService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public BigDecimal updateBalance(String openId, BigDecimal amount) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<UserDO>().eq("open_id", openId);
        UserDO userDO = userMapper.selectOne(queryWrapper);
        if (userDO == null) {
            return new BigDecimal("-1");
        }
        BigDecimal oldBalance = userDO.getBalance();
        BigDecimal newBalance = oldBalance.add(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) == -1) {
            return new BigDecimal("-1.00");
        }
        UpdateWrapper<UserDO> wrapper = new UpdateWrapper<UserDO>().set("balance", newBalance);
        userMapper.update(userDO, wrapper);
        return newBalance;
    }
}
