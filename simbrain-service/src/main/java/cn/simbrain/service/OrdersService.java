package cn.simbrain.service;

import cn.simbrain.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description 申请到活动的服务层
 * @date 2021/2/14
 */
@Service
public class OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;
}
