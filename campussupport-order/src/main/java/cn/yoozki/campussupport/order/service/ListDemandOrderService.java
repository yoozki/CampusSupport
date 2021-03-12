package cn.yoozki.campussupport.order.service;

import cn.yoozki.campussupport.order.pojo.vo.DemandOrderVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author yoozki
 * @date 2021/3/11
 */
public interface ListDemandOrderService {

    /**
     * 返回根据 tagId sexLimit 查询出来的分页结果
     * @param page
     * @param sexLimit
     * @param tagId
     * @return
     */
    IPage<DemandOrderVO> listDemandOrderVO(Page<DemandOrderVO> page, Integer sexLimit, Integer tagId);

    /**
     * 返回根据 sexLimit 查询出来的分页结果
     * @param page
     * @param sexLimit
     * @return
     */
    IPage<DemandOrderVO> listDemandOrderVO(Page<DemandOrderVO> page, Integer sexLimit);

}
