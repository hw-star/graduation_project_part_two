package cn.simbrain.controller;

import cn.simbrain.mapper.logmapper.LogSuccessMapper;
import cn.simbrain.pojo.LogDataMsg;
import cn.simbrain.pojo.LogSuccess;
import cn.simbrain.util.Jwt;
import cn.simbrain.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/3/21
 */
@RestController
@RequestMapping("/logdata")
public class LogData {

    @Autowired
    private LogSuccessMapper logSuccessMapper;

    @GetMapping("/getlogdata")
    public Result getLogData(){
        List<LogSuccess> list = logSuccessMapper.selectList(new QueryWrapper<LogSuccess>().notLike("request_url","logdata/getlogdata").notLike("request_url","chartuser/getchart").orderByDesc("id").last("LIMIT 40"));
        List<LogDataMsg> logDataMsgs = new ArrayList<>();
        for (LogSuccess item:list){
            LogDataMsg logDataMsg = new LogDataMsg();
            logDataMsg.setIp(item.getRequestIp());
            logDataMsg.setName(item.getRequestName());
            logDataMsg.setUrl(item.getRequestUrl());
            logDataMsg.setTime(item.getRequestTime());
            logDataMsgs.add(logDataMsg);
        }
        return Result.success(logDataMsgs);
    }
}
