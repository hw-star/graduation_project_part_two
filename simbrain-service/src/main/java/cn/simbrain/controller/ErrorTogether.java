package cn.simbrain.controller;

import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author huowei
 * @version 1.0.0
 * @description 统一异常处理
 * @date 2021/2/17
 */
@ControllerAdvice
public class ErrorTogether {
    /**
     * @description: 处理全局异常
     * @Param exception: 所有异常
     * @return: cn.simbrain.util.Result
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result errorReturn(Exception exception){
        exception.printStackTrace();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }
}
