package cn.yoozki.campussupport.order.service.impl;

import cn.yoozki.campussupport.order.mapper.OrderMapper;
import cn.yoozki.campussupport.order.mapper.OrderTagMapper;
import cn.yoozki.campussupport.order.mapper.TagMapper;
import cn.yoozki.campussupport.order.pojo.OrderDO;
import cn.yoozki.campussupport.order.pojo.OrderTagDO;
import cn.yoozki.campussupport.order.pojo.TagDO;
import cn.yoozki.campussupport.order.pojo.vo.RequireOrderVO;
import cn.yoozki.campussupport.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yoozki
 * @date 2021/2/10
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderTagMapper orderTagMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void insertOrder(OrderDO orderDO, List<Integer> tagIdList) {
        QueryWrapper<TagDO> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.in("id", tagIdList);
        List<TagDO> tagDOList = tagMapper.selectList(tagQueryWrapper);
        for (TagDO tagDO : tagDOList) {
            OrderTagDO orderTagDO = new OrderTagDO();
            orderTagDO.setOrderId(orderDO.getOrderId());
            orderTagDO.setTagName(tagDO.getTagName());
            Date date = new Date();
            orderTagDO.setGmtCreate(date);
            orderTagDO.setGmtModified(date);
            orderTagMapper.insert(orderTagDO);
        }
        orderMapper.insert(orderDO);
    }

    @Override
    public List<RequireOrderVO> listRequireOrderVOsByTag(long current, long size, List<Integer> tagIdList) {
        return orderMapper.listRequireOrderVOsByTag(tagIdList);
    }
}
