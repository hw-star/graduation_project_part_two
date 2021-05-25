package cn.simbrain.controller;

import cn.simbrain.pojo.Elegant;
import cn.simbrain.service.ElegantService;
import cn.simbrain.service.OrderRolesService;
import cn.simbrain.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/5/26
 */
@RestController
@RequestMapping("/elegant")
public class ElegantController {

    String[] roles = new String[]{"1","4","6"};

    @Autowired
    private ElegantService elegantService;
    @Autowired
    private OrderRolesService orderRolesService;


    //************************ 未测试！！！！！****************************************************
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
                                         @RequestParam(value = "fuzzyquery",required = false) String fuzzyquery,
                                         HttpServletRequest request){
//        boolean result = IsHaveRole.isHave(request,roles,orderRolesService);
//        if (!result)
//            return Result.failure(ResultCode.DATA_NONE);
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
}
