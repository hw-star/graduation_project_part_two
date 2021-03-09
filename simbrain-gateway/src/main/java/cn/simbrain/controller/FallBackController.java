package cn.simbrain.controller;

import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author huowei
 * @version 1.0.0
 * @description 网关-请求处理出问题-熔断
 * @date 2021/3/9
 */
@RestController
public class FallBackController {

    /**
     * @description: 熔断反馈
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/fallBackAll")
    public Result fallBackAll(){
        return Result.failure(ResultCode.INTERFACE_REQUEST_TIMEOUT);
    }
}
