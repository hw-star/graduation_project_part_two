package cn.simbrain.controller;

import cn.simbrain.pojo.Activity;
import cn.simbrain.pojo.Orders;
import cn.simbrain.pojo.User;
import cn.simbrain.service.ActivityService;
import cn.simbrain.service.OrdersService;
import cn.simbrain.service.UserService;
import cn.simbrain.util.Jwt;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    /**
     * @description: 查找某个活动报名的人员名单
     * @Param id: 活动号
     * @Param current: 当前页
     * @Param limit: 页面数量
     * @Param fuzzyquery: 关键字
     * @return: cn.simbrain.util.Result
     */
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

    /**
     * @description: 取消某用户对某活动的申请
     * @Param id: 订单ID，主键
     * @return: cn.simbrain.util.Result 
     */
    @DeleteMapping("/deleteorder/{id}")
    public Result deletedOrders(@PathVariable String id){
        boolean res = ordersService.removeById(id);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 用户申请活动
     * @Param acid: 活动号
     * @Param request: request 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/applyactivity/{acid}")
    public Result applyActivity(@PathVariable String acid, HttpServletRequest request){
        String token = request.getHeader("X-Token");
        Claims claims = Jwt.parseJwt(token);
        String id = claims.getId();
        User user = userService.getById(id);
        Activity activity = activityService.getById(acid);
        Orders orders = ordersService.getOne(new QueryWrapper<Orders>().eq("or_id",user.getUserId()).eq("or_acid",acid));
        if (orders != null)
            return Result.failure(ResultCode.DATA_EXISTED);
        Orders or = new Orders();
        or.setOrAcid(acid);
        or.setOrEmail(user.getUserEmail());
        or.setOrName(user.getUserName());
        or.setOrId(user.getUserId());
        or.setActName(activity.getActName());
        boolean res = ordersService.save(or);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 用户查询已申请活动信息
     * @Param current: 当前页数
     * @Param limit: 每页数
     * @Param fuzzyquery: 检索信息
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("userorderslist/{current}/{limit}")
    public Result getUserOrdersListPage(@PathVariable long current,
                                    @PathVariable long limit,
                                    @RequestParam(value = "fuzzyquery",required = false) String fuzzyquery,HttpServletRequest request){
        Claims claims = Jwt.parseJwt(request.getHeader("X-Token"));
        Page<Orders> ordersPage = new Page<>(current,limit);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        if (!"".equals(fuzzyquery)){
            wrapper
                    .or().like("or_acid",fuzzyquery)
                    .or().like("act_name", fuzzyquery);
        }
        wrapper.eq("or_id",claims.getSubject());
        ordersService.page(ordersPage,wrapper);
        long total = ordersPage.getTotal();
        List<Orders> records = ordersPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("orderdata",records);
        return Result.success(map);
    }

    /**
     * @description: 用户取消已报名的某个活动
     * @Param id: 订单主键
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/cancelactivity/{id}")
    public Result cancelActivity(@PathVariable String id,HttpServletRequest request){
        Claims claims = Jwt.parseJwt(request.getHeader("X-Token"));
        boolean res = ordersService.remove(new QueryWrapper<Orders>().eq("id",id).eq("or_id",claims.getSubject()));
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }
}
