package cn.yoozki.campussupport.order.mapper;

import cn.yoozki.campussupport.order.pojo.OrderDO;
import cn.yoozki.campussupport.order.pojo.vo.RequireOrderVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author yoozki
 * @date 2021/2/10
 */
public interface OrderMapper extends BaseMapper<OrderDO> {

    List<RequireOrderVO> listRequireOrderVOsByTag(List<Integer> tagIdList);


}
