package cn.yoozki.campussupport.order.controller;

import cn.yoozki.campussupport.common.enums.ErrorCodeEnum;
import cn.yoozki.campussupport.common.util.JSONResult;
import cn.yoozki.campussupport.order.mapper.OrderMapper;
import cn.yoozki.campussupport.order.message.OrderDelayChannel;
import cn.yoozki.campussupport.order.pojo.vo.DemandOrderVO;
import cn.yoozki.campussupport.order.service.ListDemandOrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

/**
 * @author yoozki
 * @date 2021/3/11
 */
@RestController
@RequestMapping("/list")
public class ListDemandOrderController {

    @Autowired
    private ListDemandOrderService listDemandOrderService;

    @GetMapping("/demand/{sexLimit}/{tagId}")
    public JSONResult pageListDemandOrder(@PathVariable Integer sexLimit, @PathVariable Integer tagId,
                                          @RequestParam(required = false, defaultValue = "0")Integer current, @RequestParam(required = false) Integer size) {
        Page<DemandOrderVO> page = new Page<>(current, size);
        if (tagId == null) {
            return JSONResult.ok(listDemandOrderService.listDemandOrderVO(page, sexLimit).getRecords());
        }
        return JSONResult.ok(listDemandOrderService.listDemandOrderVO(page, sexLimit, tagId).getRecords());
    }

}
