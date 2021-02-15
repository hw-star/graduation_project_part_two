package cn.simbrain.mapper.logmapper;

import cn.simbrain.pojo.LogFailure;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author huowei
 * @version 1.0.0
 * @description 日志持久层
 * @date 2021/2/15
 */
@Repository
@DS("slave")
public interface LogFailureMapper extends BaseMapper<LogFailure> {
}
