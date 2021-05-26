package cn.simbrain.service.LogService;

import cn.simbrain.mapper.logmapper.LogSuccessMapper;
import cn.simbrain.pojo.LogSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description 日志-失败服务层
 * @date 2021/2/15
 */
@Service
public class LogSuccessService {

    @Autowired
    private LogSuccessMapper logSuccessMapper;

    /**
     * @description: 插入操作成功的日志信息
     * @Param logSuccess: 数据
     * @return: boolean
     */
    public void insertMsg(LogSuccess logSuccess){
        logSuccessMapper.insert(logSuccess);
    }

}
