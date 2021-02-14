package cn.simbrain.controller;

import cn.simbrain.mapper.UserMapper;
import cn.simbrain.pojo.User;
import cn.simbrain.provide.EmailProvide;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huowei
 * @version 1.0.0
 * @description 用户控制层
 * @date 2021/2/11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailProvide emailProvide;

    /**
     * @description: 测试查询单个用户数据
     * @Param id: ID  主键
     * @return: User
     */
    @GetMapping("/finduser/{id}")
    public Result getUser(@PathVariable("id") long id){
        User user = userMapper.selectById(id);
        if (user != null)
            return Result.success(user);
        return Result.failure(ResultCode.USER_LOGIN_ERROR);
    }

    /**
     * @description: 测试
     * @return: java.lang.String
     */
    @RequestMapping("/test")
    public Result Test(){
        return Result.success("hello world");
    }


    /**
     * @description: 测试用户找回密码(邮箱发送密码方式)
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/send")
    public Result sendEmail() {
        boolean sendState = emailProvide.createEmail("1234556",15536869272L,"3111314916@qq.com");
        if (sendState)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }
}
