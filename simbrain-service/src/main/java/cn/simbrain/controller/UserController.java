package cn.simbrain.controller;

import cn.simbrain.mapper.UserMapper;
import cn.simbrain.pojo.User;
import cn.simbrain.pojo.login.UserLogin;
import cn.simbrain.provide.EmailProvide;
import cn.simbrain.service.UserService;
import cn.simbrain.util.Jwt;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.jdbc.StringUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
    private UserService userService;

    /**
     * @description: 测试用户找回密码(邮箱发送密码方式)
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/send")
    public Result sendEmail() {
        boolean sendState = emailProvide.createEmail(false,"1234556","15536869272","3111314916@qq.com");
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
            String token = Jwt.createJwt(user.getId().toString(),user.getUserId(),true,user.getUserName());
            Map<String,String> map = new HashMap<>();
            map.put("token",token);
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

    /**
     * @description: 条件查询带分页查询的功能(管理员查看所有用户)
     * @Param current: 当前第几页
     * @Param limit: 每页数量
     * @Param fuzzyquery: 关键字
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("userlist/{current}/{limit}")
    public Result getUsersListPage(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestParam(value = "fuzzyquery",required = false) String fuzzyquery){
        Page<User> userPage = new Page<>(current,limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!"".equals(fuzzyquery)){
            if ("男".equals(fuzzyquery)){
                wrapper.eq("user_sex",1);
            }else if("女".equals(fuzzyquery)){
                wrapper.eq("user_sex",0);
            }else if ("禁用".equals(fuzzyquery))
                wrapper.eq("user_stop",1);
            wrapper
                    .or().like("user_id",fuzzyquery)
                    .or().like("user_pwd",fuzzyquery)
                    .or().like("user_email",fuzzyquery)
                    .or().like("user_name",fuzzyquery);
        }
        wrapper.orderByDesc("user_create");
        userService.page(userPage,wrapper);
        long total = userPage.getTotal();
        List<User> records = userPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("userdata",records);
        return Result.success(map);
    }

    /**
     * @description: 删除用户
     * @Param id: 用户ID
     * @return: cn.simbrain.util.Result
     */
    @DeleteMapping("/deleteuser/{id}")
    public Result deletedUser(@PathVariable String id){
        boolean res = userService.removeById(id);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 新增用户
     * @Param user: 用户实体
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("adduser")
    public Result addUser(@RequestBody User user){
        if (user.getUserId() == null || user.getUserPwd() == null || user.getUserEmail() == null)
            return Result.failure(ResultCode.PARAM_NOT_COMPLETE);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getUserId().trim());
        User userInSql = userService.getOne(wrapper);
        if (userInSql != null)
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        boolean res = userService.save(user);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 查询某个用户
     * @Param id: ID
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/getuser/{id}")
    public Result getUser(@PathVariable String id){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id.trim());
        User user = userService.getOne(wrapper);
        if (user == null)
            return Result.failure(ResultCode.DATA_NONE);
        return Result.success(user);
    }

    /**
     * @description: 更新用户
     * @Param user: 用户实体类
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/updateuser")
    public Result updateUser(@RequestBody User user){
        User userInSql = userService.getById(user.getId());
        user.setUserId(userInSql.getUserId());
        boolean res = userService.updateById(user);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 禁用某个用户
     * @Param id: 用户ID
     * @Param stateCode: 禁用状态
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/stopuser/{id}/{stateCode}")
    public Result stopUser(@PathVariable String id,
                           @PathVariable Integer stateCode){
        User user = userService.getById(id);
        user.setUserStop(stateCode);
        boolean res = userService.updateById(user);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.INTERFACE_REQUEST_TIMEOUT);
    }

    /**
     * @description: 用户退出
     * @Param token: jwtt令牌
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/logout")
    public Result userLogOut(){
        return Result.success();
    }
}
