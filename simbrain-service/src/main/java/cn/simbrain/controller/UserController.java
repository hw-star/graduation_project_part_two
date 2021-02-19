package cn.simbrain.controller;

import cn.simbrain.mapper.UserMapper;
import cn.simbrain.pojo.User;
import cn.simbrain.provide.EmailProvide;
import cn.simbrain.service.OssService;
import cn.simbrain.service.UserService;
import cn.simbrain.util.Jwt;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.jdbc.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    private Jwt jwt;

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

    @PostMapping("/login")
    public Result userLogin(@RequestParam("id") String userId,@RequestParam("pwd") String userPwd){
        if (StringUtils.isNullOrEmpty(userId) || StringUtils.isNullOrEmpty(userPwd)){
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId.trim());
        User user = userMapper.selectOne(wrapper);
        if (user == null){
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }
        if (userPwd.equals(user.getUserPwd())){
            String token = jwt.createJwt(user.getId().toString(),userId,true);
            Map<String,String> map = new HashMap<>();
            map.put("token",token);
            Claims claims = jwt.parseJwt(token);
            map.put("解析出的ID",claims.getId());
            map.put("解析出的账号",claims.getSubject());
            return Result.success(map);
        }
        return Result.failure(ResultCode.USER_LOGIN_ERROR);
    }

    @PostMapping("/register")
    public Result insertUser(User user){
        int result = userMapper.insert(user);
        if (result > 0){
            return Result.success();
        }
        return Result.failure(ResultCode.USER_HAS_EXISTED);
    }
}
