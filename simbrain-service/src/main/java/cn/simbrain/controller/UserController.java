package cn.simbrain.controller;

import cn.simbrain.mapper.UserMapper;
import cn.simbrain.pojo.User;
import cn.simbrain.pojo.login.UserLogin;
import cn.simbrain.provide.EmailProvide;
import cn.simbrain.util.Jwt;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.jdbc.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * @description: 用户登录
     * @Param userLogin: 账号及密码
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/login")
    public Result userLogin(@RequestBody UserLogin userLogin){
        if (StringUtils.isNullOrEmpty(userLogin.getUserLoginId()) || StringUtils.isNullOrEmpty(userLogin.getUserLoginPwd())){
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userLogin.getUserLoginId().trim());
        User user = userMapper.selectOne(wrapper);
        if (user == null){
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }
        if (userLogin.getUserLoginPwd().equals(user.getUserPwd())){
            String token = Jwt.createJwt(user.getId().toString(),user.getUserId(),true);
            Map<String,String> map = new HashMap<>();
            map.put("token",token);
            /*Claims claims = jwt.parseJwt(token);
            map.put("解析出的ID",claims.getId());
            map.put("解析出的账号",claims.getSubject());*/
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

    @GetMapping("/info")
    public Result getUserInfo(@RequestParam("token") String token){
        Claims claims = Jwt.parseJwt(token);
        Map<String,String> map = new HashMap<>();
        User user = userMapper.selectById(claims.getId());
        map.put("roles","admin");
        map.put("name","admin");
        map.put("avatar",user.getUserAvatar());
        //map.put("解析出的ID",claims.getId());
        //map.put("解析出的账号",claims.getSubject());
        return Result.success(map);
    }
}
