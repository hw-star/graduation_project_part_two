package cn.simbrain.service.LogService;

import cn.simbrain.mapper.logmapper.LogSuccessMapper;
import cn.simbrain.pojo.LogSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
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
    public boolean insertMsg(LogSuccess logSuccess){
        int count = logSuccessMapper.insert(logSuccess);
        if (count > 1)
            return true;
        return false;
    }
}
