package cn.yoozki.campussupport.order.service.impl;

import cn.yoozki.campussupport.order.mapper.OrderMapper;
import cn.yoozki.campussupport.order.pojo.OrderDO;
import cn.yoozki.campussupport.order.pojo.dto.OrderInsertDTO;
import cn.yoozki.campussupport.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author yoozki
 * @date 2021/2/10
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public String insertOrder(OrderInsertDTO orderInsertDTO, String receiverOpenId) {
        String targetAddress = orderInsertDTO.getTargetAddress();
        String deliveryAddress = orderInsertDTO.getDeliveryAddress();
        String title = orderInsertDTO.getTitle();
        String detail = orderInsertDTO.getDetail();
        BigDecimal payCost = orderInsertDTO.getPayCost();
        String receiverName = orderInsertDTO.getReceiverName();
        String receiverPhone = orderInsertDTO.getReceiverPhone();
        BigDecimal reward = orderInsertDTO.getReward();
        Date deliveryTime = orderInsertDTO.getDeliveryTime();
        Integer sexLimit = orderInsertDTO.getSexLimit();
        Integer tagId = orderInsertDTO.getTagId();
        // 封装OrderDO
        OrderDO orderDO = new OrderDO();
        orderDO.setReceiverOpenId(receiverOpenId);
        orderDO.setTargetAddress(targetAddress);
        orderDO.setDeliveryAddress(deliveryAddress);
        orderDO.setTitle(title);
        orderDO.setDetail(detail);
        orderDO.setPayCost(payCost);
        orderDO.setReceiverName(receiverName);
        orderDO.setReceiverPhone(receiverPhone);
        orderDO.setReward(reward);
        orderDO.setDeliveryTime(deliveryTime);
        orderDO.setSexLimit(sexLimit);
        orderDO.setTagId(tagId);
        orderDO.setStatus(OrderDO.getUNPAID_STATUS());
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyMMddHHmmss");
        String orderId = simpleDateFormat.format(new Date());
        for (int i = 0; i < 4; i++) {
            orderId += new Random().nextInt(10);
        }
        orderDO.setOrderId(Long.parseLong(orderId));
        Date date = new Date();
        orderDO.setGmtCreate(date);
        orderDO.setGmtModified(date);
        orderMapper.insert(orderDO);
        return orderId;
    }

    @Override
    public OrderDO getOrder(Long orderId) {
        QueryWrapper<OrderDO> wrapper = new QueryWrapper<OrderDO>().eq("order_id", orderId);
        return orderMapper.selectOne(wrapper);
    }

    @Override
    public Long updateOrder(OrderDO orderDO, Integer status) {
        UpdateWrapper<OrderDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", status);
        orderMapper.update(orderDO, updateWrapper);
        return orderDO.getOrderId();
    }
}
