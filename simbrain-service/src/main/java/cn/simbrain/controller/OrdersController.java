package cn.simbrain.controller;

import cn.simbrain.pojo.Orders;
import cn.simbrain.service.OrdersService;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huowei
 * @version 1.0.0
 * @description 申请到活动的控制层
 * @date 2021/2/12
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("orderlist/{id}/{current}/{limit}")
    public Result getOrdersListPage(@PathVariable String id,
                                    @PathVariable long current,
                                    @PathVariable long limit,
                                    @RequestParam(value = "fuzzyquery",required = false) String fuzzyquery){
        Page<Orders> ordersPage = new Page<>(current,limit);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        if (!"".equals(fuzzyquery)){
            wrapper
                    .or().like("or_id",fuzzyquery)
                    .or().like("or_email",fuzzyquery)
                    .or().like("or_name",fuzzyquery);
        }
        wrapper.eq("or_acid",id);
        int number = ordersService.count(new QueryWrapper<Orders>().eq("or_acid",id));
        ordersService.page(ordersPage,wrapper);
        long total = ordersPage.getTotal();
        List<Orders> records = ordersPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("ordersdata",records);
        map.put("applyed",number);
        return Result.success(map);
    }

    @DeleteMapping("/deleteorder/{id}")
    public Result deletedOrders(@PathVariable String id){
        boolean res = ordersService.removeById(id);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }
}
