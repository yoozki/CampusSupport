package cn.yoozki.campussupport.user.api;

import java.math.BigDecimal;

/**
 * @author yoozki
 * @date 2021/3/10
 */
public interface UserBalanceService {

    BigDecimal updateBalance(String openId, BigDecimal amount);

}
