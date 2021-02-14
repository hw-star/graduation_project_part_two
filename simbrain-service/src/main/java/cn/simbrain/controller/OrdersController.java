package cn.simbrain.controller;

import cn.simbrain.mapper.OrdersMapper;
import cn.simbrain.pojo.Orders;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huowei
 * @version 1.0.0
 * @description 申请到活动的控制层
 * @date 2021/2/12
 */

@RestController
public class OrdersController {

    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * @description: 测试查询单个申请数据
     * @Param id: ID  主键
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/findorder/{id}")
    public Result getOrder(@PathVariable("id") Long id){
        Orders orders = ordersMapper.selectById(id);
        if (orders != null)
            return Result.success(orders);
        return Result.failure(ResultCode.DATA_NONE);
    }
}
