package cn.simbrain.controller;

import cn.simbrain.mapper.OrdersMapper;
import cn.simbrain.pojo.Orders;
import cn.simbrain.pojo.User;
import cn.simbrain.service.OrdersService;
import cn.simbrain.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jdk.nashorn.internal.objects.annotations.ScriptClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huowei
 * @version 1.0.0
 * @description 申请到活动的控制层
 * @date 2021/2/12
 */
@CrossOrigin
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
        QueryWrapper<Orders> wrapperForApplyed = new QueryWrapper<>();
        if (!"".equals(fuzzyquery)){
            wrapper
                    .or().like("or_id",fuzzyquery)
                    .or().like("or_email",fuzzyquery)
                    .or().like("or_name",fuzzyquery);
        }
        wrapper.eq("or_acid",id);
        wrapperForApplyed.eq("or_acid",id);
        int number = ordersService.count(wrapperForApplyed);
        ordersService.page(ordersPage,wrapper);
        long total = ordersPage.getTotal();
        List<Orders> records = ordersPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("ordersdata",records);
        map.put("applyed",number);
        return Result.success(map);
    }
}
