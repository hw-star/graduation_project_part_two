package cn.simbrain.controller;

import cn.simbrain.mapper.ActivityMapper;
import cn.simbrain.pojo.Activity;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author huowei
 * @version 1.0.0
 * @description 活动控制层
 * @date 2021/2/12
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityMapper activityMapper;

    /**
     * @description: 测试查询单个活动数据
     * @Param id: ID  主键
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/findactivity/{id}")
    public Result getActivity(@PathVariable("id") Long id){
        Activity activity = activityMapper.selectById(id);
        if (activity != null)
            return Result.success(activity);
        return Result.failure(ResultCode.DATA_NONE);
    }

    @PostMapping("/addactivity")
    public Result setActivity(Activity activity){
         int result = activityMapper.insert(activity);
         if (result > 0)
             return Result.success();
         return Result.failure(ResultCode.USER_HAS_EXISTED);
    }
}
