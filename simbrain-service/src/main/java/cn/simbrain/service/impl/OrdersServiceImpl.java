package cn.simbrain.service.impl;

import cn.simbrain.mapper.OrdersMapper;
import cn.simbrain.pojo.Orders;
import cn.simbrain.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/2/19
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
