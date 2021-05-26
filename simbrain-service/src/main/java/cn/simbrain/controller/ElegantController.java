package cn.simbrain.controller;

import cn.simbrain.pojo.Elegant;
import cn.simbrain.service.ElegantService;
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
 * @description 志愿风采控制层
 * @date 2021/5/26
 */
@RestController
@RequestMapping("/elegant")
public class ElegantController {

    @Autowired
    private ElegantService elegantService;


    /**
     * @description: 条件带分页查询志愿风采(管理员使用)
     * @Param current: 当前第几页
     * @Param limit: 页面数量
     * @Param fuzzyquery: 关键字
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("elegantlistbysys/{current}/{limit}")
    public Result getElegantListPageBySys(@PathVariable long current,
                                         @PathVariable long limit,
                                         @RequestParam(value = "fuzzyquery",required = false) String fuzzyquery){
        Page<Elegant> elegantPage = new Page<>(current,limit);
        QueryWrapper<Elegant> wrapper = new QueryWrapper<>();
        if (!"".equals(fuzzyquery)){
            wrapper
                    .or().like("el_name",fuzzyquery)
                    .or().like("el_time",fuzzyquery);
        }
        wrapper.orderByDesc("el_time");
        elegantService.page(elegantPage,wrapper);
        long total = elegantPage.getTotal();
        List<Elegant> records = elegantPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("elegantdata",records);
        return Result.success(map);
    }

    /**
     * @description: 开启或关闭某个志愿风采
     * @Param id: ID
     * @Param stateCode: 状态码
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/showelegant/{id}/{stateCode}")
    public Result showElegant(@PathVariable String id,
                             @PathVariable Integer stateCode){
        Elegant elegant = elegantService.getById(id);
        elegant.setElActive(stateCode);
        boolean res = elegantService.updateById(elegant);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 删除某个志愿风采
     * @Param id: ID
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @DeleteMapping("/deleteelegant/{id}")
    public Result deletedElegant(@PathVariable String id){
        boolean res = elegantService.removeById(id);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 批量删除志愿风采
     * @Param ids: ID
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/moredeleteelegant")
    public Result moreDeleteElegant(@RequestBody String[] ids){
        boolean res = elegantService.removeByIds(Arrays.asList(ids));
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 增加志愿风采
     * @Param notice: 志愿风采实体
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/saveelegant")
    public Result addElegant(@RequestBody Elegant elegant){
        if (elegant.getElName() == null || elegant.getElTime() == null){
            return Result.failure(ResultCode.PARAM_NOT_COMPLETE);
        }
        boolean res = elegantService.save(elegant);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 更新志愿风采
     * @Param policy: 志愿风采实体
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/updateelegant")
    public Result updateElegant(@RequestBody Elegant elegant){
        boolean res = elegantService.updateById(elegant);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 获取某一个志愿风采
     * @Param id: ID
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/elegantlist/{id}")
    public Result getOneElegant(@PathVariable Integer id, HttpServletRequest request){
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
            }
        }
        Elegant elegant = elegantService.getById(id);
        if (elegant == null)
            return Result.failure(ResultCode.DATA_NONE);
        return Result.success(elegant);
    }

    /**
     * @description: 分页查询全部志愿风采(不带任何条件查询)
     * @Param current: 当前第几页
     * @Param limit: 每页数量
     * @Param request: 请求
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("elegantlist/{current}/{limit}")
    public Result getElegantListPage(@PathVariable long current,
                                    @PathVariable long limit){
        Page<Elegant> elegantPage = new Page<>(current,limit);
        QueryWrapper<Elegant> wrapper = new QueryWrapper<>();
        wrapper.eq("el_active",1).orderByDesc("el_time");
        elegantService.page(elegantPage,wrapper);
        long total = elegantPage.getTotal();
        List<Elegant> records = elegantPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("elegantdata",records);
        return Result.success(map);
    }
}
