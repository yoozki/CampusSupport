package cn.yoozki.campussupport.order.service;

import cn.yoozki.campussupport.order.pojo.OrderDO;
import cn.yoozki.campussupport.order.pojo.vo.RequireOrderVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yoozki
 * @date 2021/2/10
 */
public interface OrderService {

    /**
     * 插入到表order_info
     * @param orderDO
     * @param tagIdList 标签集合
     */
    void insertOrder(OrderDO orderDO, List<Integer> tagIdList);

    /**
     * 传入当前页 每页显示条数 返回状态为待接单的订单集合
     * @param current 当前页
     * @param size 每页显示条数
     * @return 状态为待接单的订单List
     */
    List<RequireOrderVO> listRequireOrderVOsByTag(long current, long size, List<Integer> tagIdList);

}
