package cn.simbrain.controller;

import cn.simbrain.pojo.SysUser;
import cn.simbrain.pojo.User;
import cn.simbrain.pojo.login.SysUserLogin;
import cn.simbrain.service.SysUserService;
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
 * @description 管理员控制层
 * @date 2021/2/12
 */
@CrossOrigin
@RestController
@RequestMapping("/sysuser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private Jwt jwt;

    /**
     * @description: 管理员登录功能
     * @Param sysUserLogin: 账号密码
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/login")
    public Result sysUserLogin(@RequestBody SysUserLogin sysUserLogin){
        if (StringUtils.isNullOrEmpty(sysUserLogin.getSysUserLoginId()) || StringUtils.isNullOrEmpty(sysUserLogin.getSysUserLoginPwd())){
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("sys_id",sysUserLogin.getSysUserLoginId().trim());
        SysUser sysUser = sysUserService.getOne(wrapper);
        if (sysUser == null){
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }
        if (sysUserLogin.getSysUserLoginPwd().equals(sysUser.getSysPwd())){
            String token = jwt.createJwt(sysUser.getId().toString(),sysUserLogin.getSysUserLoginId(),true);
            Map<String,String> map = new HashMap<>();
            map.put("token",token);
            return Result.success(map);
        }
        return Result.failure(ResultCode.USER_LOGIN_ERROR);
    }

    /**
     * @description: 获取登录者信息功能
     * @Param token: 身份令牌
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/info")
    public Result getInfo(@RequestParam("token") String token){
        Claims claims = jwt.parseJwt(token);
        Map<String,String> map = new HashMap<>();
        map.put("roles","admin");
        map.put("name","admin");
        map.put("解析出的ID",claims.getId());
        map.put("解析出的账号",claims.getSubject());
        map.put("avatar","https://youngvolunteer.oss-cn-beijing.aliyuncs.com/common.gif");
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
            }
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


}
