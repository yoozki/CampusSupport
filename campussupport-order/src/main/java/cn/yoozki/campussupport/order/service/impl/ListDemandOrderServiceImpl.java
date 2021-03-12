package cn.yoozki.campussupport.order.service.impl;

import cn.yoozki.campussupport.order.mapper.OrderMapper;
import cn.yoozki.campussupport.order.pojo.vo.DemandOrderVO;
import cn.yoozki.campussupport.order.service.ListDemandOrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yoozki
 * @date 2021/3/11
 */
@Service
public class ListDemandOrderServiceImpl implements ListDemandOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public IPage<DemandOrderVO> listDemandOrderVO(Page<DemandOrderVO> page, Integer sexLimit) {
        return orderMapper.listDemandOrderVO(page, sexLimit);
    }

    @Override
    public IPage<DemandOrderVO> listDemandOrderVO(Page<DemandOrderVO> page, Integer sexLimit, Integer tagId) {
        return orderMapper.listTagDemandOrderVO(page, sexLimit, tagId);
    }
}
