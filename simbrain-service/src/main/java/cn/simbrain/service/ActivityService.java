package cn.simbrain.service;

import cn.simbrain.mapper.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description 活动服务层
 * @date 2021/2/14
 */
@Service
public class ActivityService {

    @Autowired
    private ActivityMapper activityMapper;
}
