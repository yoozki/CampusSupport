package cn.yoozki.campussupport.order.controller;

import cn.yoozki.campussupport.order.common.OrderConst;
import cn.yoozki.campussupport.order.pojo.OrderDO;
import cn.yoozki.campussupport.order.pojo.OrderDeliveryDO;
import cn.yoozki.campussupport.order.pojo.dto.OrderInsertDTO;
import cn.yoozki.campussupport.order.service.OrderDeliveryService;
import cn.yoozki.campussupport.order.service.OrderService;
import cn.yoozki.campussupport.common.enums.ErrorCodeEnum;
import cn.yoozki.campussupport.common.pojo.UserTokenDTO;
import cn.yoozki.campussupport.common.util.JSONResult;
import cn.yoozki.campussupport.common.util.JwtUtils;
import cn.yoozki.campussupport.user.api.UserBalanceService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @author yoozki
 * @date 2021/2/10
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDeliveryService orderDeliveryService;

    @DubboReference
    private UserBalanceService userBalanceService;


    @PostMapping()
    public JSONResult insertOrder(@RequestHeader("Authorization") String token, @Validated @RequestBody OrderInsertDTO orderInsertDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getMsg(), bindingResult.getAllErrors());
        }
        UserTokenDTO userTokenDTO = null;
        try {
            userTokenDTO = JwtUtils.parseSubject(token);
        } catch (Exception e) {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
        String openId = userTokenDTO.getOpenId();
        Long orderId = orderService.insertOrder(orderInsertDTO, openId);
        return JSONResult.ok(orderId);
    }

    @DeleteMapping("/{orderId}")
    public JSONResult removeOrder(@RequestHeader("Authorization") String token, @PathVariable("orderId") Long orderId) {
        UserTokenDTO userTokenDTO = null;
        try {
            userTokenDTO = JwtUtils.parseSubject(token);
        } catch (Exception e) {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
        OrderDO orderDO = orderService.getOrder(orderId);
        if (orderDO == null) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getMsg());
        }
        String receiverOpenId = orderDO.getReceiverOpenId();
        if (!receiverOpenId.equals(userTokenDTO.getOpenId()) || orderDO.getStatus() >= OrderConst.WAIT_STATUS) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getMsg());
        }
        orderService.updateOrder(orderDO, OrderConst.CANCEL_STATUS);
        return JSONResult.ok();
    }

    @PutMapping("/payment/{orderId}")
    public JSONResult paymentOrder(@RequestHeader("Authorization") String token, @PathVariable("orderId") Long orderId) {
        UserTokenDTO userTokenDTO = null;
        try {
            userTokenDTO = JwtUtils.parseSubject(token);
        } catch (Exception e) {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
        OrderDO orderDO = orderService.getOrder(orderId);
        if (orderDO == null) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getMsg());
        }
        String receiverOpenId = orderDO.getReceiverOpenId();
        if (!receiverOpenId.equals(userTokenDTO.getOpenId()) || !OrderConst.UNPAID_STATUS.equals(orderDO.getStatus())) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getCode(), ErrorCodeEnum.USER_REQUEST_PARAM_ERROR.getMsg());
        }
        orderService.updateOrder(orderDO, OrderConst.WAIT_STATUS);
        BigDecimal amount = orderDO.getReward().negate();
        BigDecimal compareValue = new BigDecimal("-1.00");
        BigDecimal resultBalance = userBalanceService.updateBalance(receiverOpenId, amount);
        if (compareValue.compareTo(resultBalance) == 0) {
            return JSONResult.errorMsg(ErrorCodeEnum.USER_BALANCE_INSUFFICIENT.getCode(), ErrorCodeEnum.USER_BALANCE_INSUFFICIENT.getMsg());
        }
        return JSONResult.ok(resultBalance);
    }

    @PostMapping("/delivery/{orderId}")
    public JSONResult deliveryOrder(@RequestHeader("Authorization") String token, @PathVariable Long orderId) {
        UserTokenDTO userTokenDTO = JwtUtils.parseSubject(token);
        if (userTokenDTO == null) {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
        OrderDO orderDO = orderService.getOrder(orderId);
        String deliveryOpenId = userTokenDTO.getOpenId();
        if (!orderDO.getStatus().equals(OrderConst.WAIT_STATUS)) {
            return JSONResult.errorMsg(ErrorCodeEnum.ORDER_STATUS_ERROR.getCode(), ErrorCodeEnum.ORDER_STATUS_ERROR.getMsg());
        }
        Long result = orderDeliveryService.insertOrderDeliveryDO(deliveryOpenId, orderDO);
        return JSONResult.ok(result);
    }

    @PutMapping("/complete/{orderId}")
    public JSONResult completeOrder(@RequestHeader("Authorization") String token, @PathVariable Long orderId, @RequestParam Integer code) {
        UserTokenDTO userTokenDTO = JwtUtils.parseSubject(token);
        if (userTokenDTO == null) {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
        OrderDeliveryDO orderDeliveryDO = orderDeliveryService.getOrderDeliveryDO(orderId);
        if (orderDeliveryDO == null) {
            return JSONResult.errorMsg(ErrorCodeEnum.ORDER_STATUS_ERROR.getCode(), ErrorCodeEnum.ORDER_STATUS_ERROR.getMsg());
        }
        Integer trueCode = orderDeliveryDO.getCode();
        String deliveryOpenId = orderDeliveryDO.getDeliveryOpenId();
        if (!deliveryOpenId.equals(userTokenDTO.getOpenId())) {
            return JSONResult.errorMsg(ErrorCodeEnum.TOKEN_VERIFY_ERROR.getCode(), ErrorCodeEnum.TOKEN_VERIFY_ERROR.getMsg());
        }
        if (!trueCode.equals(code)) {
            return JSONResult.errorMsg(ErrorCodeEnum.ORDER_CODE_ERROR.getCode(), ErrorCodeEnum.ORDER_CODE_ERROR.getMsg());
        }
        OrderDO orderDO = orderService.getOrder(orderId);
        orderService.updateOrder(orderDO, OrderConst.COMPLETE_STATUS);
        return JSONResult.ok();
    }


}
