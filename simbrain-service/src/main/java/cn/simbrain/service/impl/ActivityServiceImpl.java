package cn.simbrain.service.impl;

import cn.simbrain.mapper.ActivityMapper;
import cn.simbrain.mapper.OrdersMapper;
import cn.simbrain.pojo.Activity;
import cn.simbrain.pojo.ActivityBody;
import cn.simbrain.pojo.Orders;
import cn.simbrain.service.ActivityService;
import cn.simbrain.service.OrdersService;
import cn.simbrain.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/2/19
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private OrdersService ordersService;

    @Override
    public Result getUsersListPage(long current, long limit) {
        return this.getUsersListPage(current,limit,new ActivityBody("","",""));
    }

    /**
     * @description: 条件查询带分页查询的功能
     * @Param current: 当前页
     * @Param limit: 每页数量
     * @Param activityBody: 关键字
     * @return: cn.simbrain.util.Result
     */
    @Override
    public Result getUsersListPage(long current, long limit, ActivityBody activityBody) {
        Page<Activity> activityPage = new Page<>(current,limit);
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        String fuzzyquery = activityBody.getFuzzyquery();
        String begin = activityBody.getBegin();
        String end = activityBody.getEnd();
        if (begin != null)
            wrapper.ge("act_update",begin);
        if (end != null)
            wrapper.le("act_update",end);
        if (begin != null && end != null){
            if (fuzzyquery != null){
                if (StringUtils.isNumeric(fuzzyquery)){
                    wrapper.like("id",fuzzyquery);
                }else {
                    wrapper.like("act_name",fuzzyquery);
                }
            }
        }else {
            if (fuzzyquery != null){
                wrapper
                        .or().like("id",fuzzyquery)
                        .or().like("act_name",fuzzyquery);
            }
        }
        wrapper.orderByDesc("act_update");
        activityMapper.selectPage(activityPage,wrapper);
        long total = activityPage.getTotal();
        List<Activity> records = activityPage.getRecords();
        for (Activity list:records){
            int num = ordersService.count(new QueryWrapper<Orders>().eq("or_acid",list.getId()));
            list.setActNumbered(num);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("activitydata",records);
        return Result.success(map);
    }


}
