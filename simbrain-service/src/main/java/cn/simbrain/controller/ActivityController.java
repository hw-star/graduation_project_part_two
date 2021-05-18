package cn.simbrain.controller;

import cn.simbrain.pojo.Activity;
import cn.simbrain.pojo.ActivityBody;
import cn.simbrain.pojo.OrderRoles;
import cn.simbrain.provide.IsHaveRole;
import cn.simbrain.service.ActivityService;
import cn.simbrain.service.OrderRolesService;
import cn.simbrain.util.Jwt;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huowei
 * @version 1.0.0
 * @description 活动控制层
 * @date 2021/2/12
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    String[] roles = new String[]{"1","3","4"};

    @Autowired
    private ActivityService activityService;
    @Autowired
    private OrderRolesService orderRolesService;

    /**
     * @description: 条件查询带分页查询的功能(活动)
     * @Param current: 当前页
     * @Param limit: 页数
     * @Param activityBody: 关键字以及开始结束日期
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("activitylist/{current}/{limit}")
    public Result getUsersListPage(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestBody(required = false) ActivityBody activityBody,
                                   HttpServletRequest request) {
        int num = 0;
        String token = request.getHeader("X-Token");
        if (token == null){
            num = 1;
        }else {
            Claims claims = Jwt.parseJwt(request.getHeader("X-Token"));
            boolean isUser = (boolean) claims.get("user");
            if (isUser){
                num = 1;
            }else {
                boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
                if (!result)
                    return Result.failure(ResultCode.DATA_NONE);
            }
        }
        if (activityBody != null)
            return activityService.getUsersListPage(current, limit, activityBody, num);
        return activityService.getUsersListPage(current, limit, num);

    }

    /**
     * @description: 删除某个活动
     * @Param id: 活动ID
     * @return: cn.simbrain.util.Result
     */
    @DeleteMapping("/deleteactivity/{id}")
    public Result deletedActivity(@PathVariable String id, HttpServletRequest request){
        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
        if (!result)
            return Result.failure(ResultCode.DATA_NONE);
        boolean res = activityService.removeById(id);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 获取某个活动
     * @Param id: 活动ID
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/getactivity/{id}")
    public Result getActivity(@PathVariable String id, HttpServletRequest request){
        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
        if (!result)
            return Result.failure(ResultCode.DATA_NONE);
        Activity activity = activityService.getById(id);
        if (activity != null)
            return Result.success(activity);
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 增加活动
     * @Param activity: 活动实体
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/saveActivity")
    public Result addActivity(@RequestBody Activity activity, HttpServletRequest request){
        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
        if (!result)
            return Result.failure(ResultCode.DATA_NONE);
        if (activity.getActName() == null || activity.getActDescription() == null || activity.getActNumber() == null){
            return Result.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        if (activity.getActNumber() >= 1){
            boolean res = activityService.save(activity);
            if (res)
                return Result.success();
        }
        return Result.failure(ResultCode.PARAM_NOT_COMPLETE);
    }

    /**
     * @description: 开启或关闭某个活动
     * @Param id: 活动号
     * @Param stateCode: 状态码
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/stopactivity/{id}/{stateCode}")
    public Result stopActivity(@PathVariable String id,
                           @PathVariable Integer stateCode,
                               HttpServletRequest request){
        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
        if (!result)
            return Result.failure(ResultCode.DATA_NONE);
        Activity activity = activityService.getById(id);
        activity.setActActive(stateCode);
        boolean res = activityService.updateById(activity);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.INTERFACE_REQUEST_TIMEOUT);
    }

    /**
     * @description: 更新某个活动
     * @Param activity: 活动实体类
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/updateactivity")
    public Result updateActivity(@RequestBody Activity activity, HttpServletRequest request){
        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
        if (!result)
            return Result.failure(ResultCode.DATA_NONE);
        boolean res = activityService.updateById(activity);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }
}
