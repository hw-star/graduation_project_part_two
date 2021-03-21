package cn.simbrain.service.LogService;

import cn.simbrain.mapper.logmapper.LogFailureMapper;
import cn.simbrain.pojo.LogFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description 日志服务层
 * @date 2021/2/15
 */
@Service
public class LogFailureService {

    @Autowired
    private LogFailureMapper logFailureMapper;

    /**
     * @description: 插入操作失败的日志信息
     * @Param logFailure: 数据
     * @return: boolean
     */
    public void insertMsg(LogFailure logFailure){
        logFailureMapper.insert(logFailure);
    }
}
