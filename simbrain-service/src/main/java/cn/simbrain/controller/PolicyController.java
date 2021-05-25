package cn.simbrain.controller;

import cn.simbrain.pojo.Policy;
import cn.simbrain.provide.IsHaveRole;
import cn.simbrain.service.OrderRolesService;
import cn.simbrain.service.PolicyService;
import cn.simbrain.util.Jwt;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/5/22
 */
@RestController
@RequestMapping("/policy")
public class PolicyController {

    String[] roles = new String[]{"1","4","6"};

    @Autowired
    private PolicyService policyService;
    @Autowired
    private OrderRolesService orderRolesService;

    /**
     * @description: 分页查询全部文件(不带任何条件查询)
     * @Param current: 当前第几页
     * @Param limit: 每页数量
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("policylist/{current}/{limit}")
    public Result getPolicyListPage(@PathVariable long current,
                                   @PathVariable long limit){
        Page<Policy> policyPage = new Page<>(current,limit);
        QueryWrapper<Policy> wrapper = new QueryWrapper<>();
        wrapper.eq("po_active",1).orderByDesc("po_time");
        policyService.page(policyPage,wrapper);
        long total = policyPage.getTotal();
        List<Policy> records = policyPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("policydata",records);
        return Result.success(map);
    }

    /**
     * @description: 获取某一个政策文件
     * @Param id: ID
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/policylist/{id}")
    public Result getOnePolicy(@PathVariable Integer id, HttpServletRequest request){
        // 1:普通用户 0:管理员
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
        Policy policy = policyService.getById(id);
        if (policy == null)
            return Result.failure(ResultCode.DATA_NONE);
        return Result.success(policy);
    }

    /**
     * @description: 条件带分页查询文件(管理员使用)
     * @Param current: 当前第几页
     * @Param limit: 页面数量
     * @Param fuzzyquery: 关键字
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("policylistbysys/{current}/{limit}")
    public Result getPolicyListPageBySys(@PathVariable long current,
                                         @PathVariable long limit,
                                         @RequestParam(value = "fuzzyquery",required = false) String fuzzyquery,
                                         HttpServletRequest request){
        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
        if (!result)
            return Result.failure(ResultCode.DATA_NONE);
        Page<Policy> policyPage = new Page<>(current,limit);
        QueryWrapper<Policy> wrapper = new QueryWrapper<>();
        if (!"".equals(fuzzyquery)){
            wrapper
                    .or().like("po_title",fuzzyquery)
                    .or().like("po_source",fuzzyquery);
        }
        wrapper.orderByDesc("po_time");
        policyService.page(policyPage,wrapper);
        long total = policyPage.getTotal();
        List<Policy> records = policyPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("policydata",records);
        return Result.success(map);
    }

    /**
     * @description: 开启或关闭某个政策文件
     * @Param id: ID
     * @Param stateCode: 状态码
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/showpolicy/{id}/{stateCode}")
    public Result showPolicy(@PathVariable String id,
                               @PathVariable Integer stateCode,
                               HttpServletRequest request){
        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
        if (!result)
            return Result.failure(ResultCode.DATA_NONE);
        Policy policy = policyService.getById(id);
        policy.setPoActive(stateCode);
        boolean res = policyService.updateById(policy);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.INTERFACE_REQUEST_TIMEOUT);
    }

    /**
     * @description: 删除某个政策文件
     * @Param id: ID
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @DeleteMapping("/deletepolicy/{id}")
    public Result deletedPolicy(@PathVariable String id, HttpServletRequest request){
        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
        if (!result)
            return Result.failure(ResultCode.DATA_NONE);
        boolean res = policyService.removeById(id);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 批量删除政策文件
     * @Param ids: ID
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/moredeletepolicy")
    public Result moreDeletePolicy(@RequestBody String[] ids, HttpServletRequest request){
        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
        if (!result)
            return Result.failure(ResultCode.DATA_NONE);
        boolean res = policyService.removeByIds(Arrays.asList(ids));
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 增加政策文件
     * @Param policy: 政策文件实体
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/savepolicy")
    public Result addPolicy(@RequestBody Policy policy, HttpServletRequest request){
        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
        if (!result)
            return Result.failure(ResultCode.DATA_NONE);
        if (policy.getPoTitle() == null || policy.getPoContent() == null || policy.getPoSource() == null){
            return Result.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        boolean res = policyService.save(policy);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.PARAM_NOT_COMPLETE);
    }

    /**
     * @description: 更新政策文件
     * @Param policy: 政策文件实体
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/updatepolicy")
    public Result updatePolicy(@RequestBody Policy policy, HttpServletRequest request){
        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
        if (!result)
            return Result.failure(ResultCode.DATA_NONE);
        boolean res = policyService.updateById(policy);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }
}
