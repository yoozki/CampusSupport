package cn.yoozki.campussupport.order.mapper;

import cn.yoozki.campussupport.order.pojo.OrderDO;
import cn.yoozki.campussupport.order.pojo.vo.DemandOrderVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yoozki
 * @date 2021/2/10
 */
public interface OrderMapper extends BaseMapper<OrderDO> {

    /**
     * 根据 sexLimit 返回需要接单的分页list
     * @param sexLimit 性别限制
     * @return 需要接单的分页list
     */
    IPage<DemandOrderVO> listDemandOrderVO(Page<DemandOrderVO> page, @Param("sexLimit") Integer sexLimit);

    /**
     * 根据 sexLimit tagId 返回需要接单的分页list
     * @param sexLimit 性别限制
     * @param tagId 标签id
     * @return 需要接单的分页list
     */
    IPage<DemandOrderVO> listTagDemandOrderVO(Page<DemandOrderVO> page, @Param("sexLimit") Integer sexLimit, @Param("tagId") Integer tagId);


}
