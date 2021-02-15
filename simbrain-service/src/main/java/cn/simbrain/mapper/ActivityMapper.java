package cn.simbrain.mapper;

import cn.simbrain.pojo.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.baomidou.dynamic.datasource.annotation.DS;

/**
 * @author huowei
 * @version 1.0.0
 * @description 活动持久层
 * @date 2021/2/12
 */
@Repository
public interface ActivityMapper extends BaseMapper<Activity> {
}
