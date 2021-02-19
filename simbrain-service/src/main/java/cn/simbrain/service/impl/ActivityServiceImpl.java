package cn.simbrain.service.impl;

import cn.simbrain.mapper.ActivityMapper;
import cn.simbrain.pojo.Activity;
import cn.simbrain.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/2/19
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
}
