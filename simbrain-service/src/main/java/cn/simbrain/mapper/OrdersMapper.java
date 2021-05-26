package cn.simbrain.mapper;

import cn.simbrain.pojo.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author huowei
 * @version 1.0.0
 * @description 申请活动持久层
 * @date 2021/2/12
 */
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {
}
