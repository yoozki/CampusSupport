package cn.yoozki.campussupport.order.controller;

import cn.yoozki.campussupport.order.pojo.OrderDO;
import cn.yoozki.campussupport.order.pojo.dto.OrderInsertDTO;
import cn.yoozki.campussupport.order.pojo.vo.RequireOrderVO;
import cn.yoozki.campussupport.order.service.OrderService;
import cn.yoozki.campussupport.common.enums.ErrorCodeEnum;
import cn.yoozki.campussupport.common.pojo.UserTokenDTO;
import cn.yoozki.campussupport.common.util.JSONResult;
import cn.yoozki.campussupport.common.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author yoozki
 * @date 2021/2/10
 */
@RestController
public class OrderController {

    private static final String AUTHORIZE_TOKEN_HEADER = "Authorization";

    @Autowired
    private OrderService orderService;

    @GetMapping("/requireOrder")
    public JSONResult listRequireOrders(@RequestParam List<Integer> tagId) {
        List<RequireOrderVO> requireOrderVOList = orderService.listRequireOrderVOsByTag(1, 2, tagId);
        return JSONResult.ok(requireOrderVOList);
    }

    @PostMapping("/order")
    public JSONResult insertOrder(@RequestHeader(AUTHORIZE_TOKEN_HEADER) String token, @RequestBody OrderInsertDTO orderInsertDTO) throws Exception {
        UserTokenDTO userTokenDTO = JwtUtils.parseSubject(token);
        String avatar = userTokenDTO.getAvatar();
        String nickname = userTokenDTO.getOpenId();
        String username = userTokenDTO.getUsername();
        if (StringUtils.isAllBlank(orderInsertDTO.getOrderTitle(), orderInsertDTO.getOrderDetail()) || orderInsertDTO.getLimitTime() == null
                || orderInsertDTO.getDeliverCost() == null || orderInsertDTO.getPayCost() == null) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getMsg());
        }
        OrderDO orderDO = createOrderDO(orderInsertDTO);
        Date date = new Date();
        orderDO.setGmtCreate(date);
        orderDO.setGmtModified(date);
        orderDO.setReceiveUserAvatar(avatar);
        orderDO.setReceiveUserNickname(nickname);
        orderDO.setReceiveUserId(username);
        orderDO.setStatus(1);
        orderService.insertOrder(orderDO, orderInsertDTO.getTagIdList());
        return JSONResult.ok(orderDO);
    }

    private OrderDO createOrderDO(OrderInsertDTO orderInsertDTO) throws Exception {
        OrderDO orderDO = new OrderDO();
        orderDO.setOrderTitle(orderInsertDTO.getOrderTitle());
        orderDO.setOrderDetail(orderInsertDTO.getOrderDetail());
        orderDO.setLimitTime(orderInsertDTO.getLimitTime());
        orderDO.setDeliverCost(orderInsertDTO.getDeliverCost());
        orderDO.setPayCost(orderInsertDTO.getPayCost());
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyMMddHHmmss");
        String orderId = simpleDateFormat.format(new Date());
        for (int i = 0; i < 4; i++) {
            orderId += new Random().nextInt(10);
        }
        orderDO.setOrderId(Long.parseLong(orderId));
        return orderDO;
    }
}
