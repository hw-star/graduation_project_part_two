package cn.simbrain.service;


import cn.simbrain.pojo.Activity;
import cn.simbrain.pojo.ActivityBody;
import cn.simbrain.util.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author huowei
 * @version 1.0.0
 * @description 活动服务层
 * @date 2021/2/14
 */
public interface ActivityService extends IService<Activity> {

    /**
     * @description: 条件查询带分页查询的功能
     * @Param current: 当前第几页
     * @Param limit: 每页数量
     * @return: cn.simbrain.util.Result 
     */
    Result getUsersListPage(long current,long limit, String id);
    Result getUsersListPage(long current, long limit, ActivityBody activityBody, String id);
}
