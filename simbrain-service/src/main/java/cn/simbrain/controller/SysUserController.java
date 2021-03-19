package cn.simbrain.controller;

import cn.simbrain.pojo.*;
import cn.simbrain.pojo.login.SysUserLogin;
import cn.simbrain.service.OrderRolesService;
import cn.simbrain.service.RoleService;
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
@RestController
@RequestMapping("/sysuser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRolesService orderRolesService;
    @Autowired
    private RoleService roleService;


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
        if (sysUserLogin.getSysUserLoginPwd().equals(sysUser.getSysPwd())) {
            String token = Jwt.createJwt(sysUser.getId().toString(), sysUserLogin.getSysUserLoginId(), true);
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
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
        Claims claims = Jwt.parseJwt(token);
        Map<String,Object> map = new HashMap<>();
        String[] roles = new String[1];
        roles[0] = "admin";
        map.put("roles", roles);
        map.put("name","admin");
        map.put("解析出的ID",claims.getId());
        SysUser sysUser = sysUserService.getById(claims.getId());
        map.put("解析出的账号",claims.getSubject());
        map.put("sysUser",sysUser);
        map.put("avatar","https://youngvolunteer.oss-cn-beijing.aliyuncs.com/common.gif");
        return Result.success(map);
    }

    /**
     * @description: 修改个人信息
     * @Param sysUser: 管理员实体类
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/update")
    public Result updateUser(@RequestBody SysUser sysUser){
        SysUser sysUserInSql = sysUserService.getById(sysUser.getId());
        sysUser.setSysId(sysUserInSql.getSysId());
        boolean res = sysUserService.updateById(sysUser);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 条件查询带分页查询
     * @Param current: 当前第几页
     * @Param limit: 每页数量
     * @Param fuzzyquery: 关键字
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("sysuserlist/{current}/{limit}")
    public Result getsysUsersListPage(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestParam(value = "fuzzyquery",required = false) String fuzzyquery){
        Page<SysUser> sysUserPage = new Page<>(current,limit);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        if (!"".equals(fuzzyquery)){
           if ("禁用".equals(fuzzyquery))
                wrapper.eq("sys_stop",1);
            wrapper
                    .or().like("sys_id",fuzzyquery)
                    .or().like("sys_pwd",fuzzyquery);
        }
        wrapper.orderByDesc("sys_create");
        sysUserService.page(sysUserPage,wrapper);
        long total = sysUserPage.getTotal();
        List<SysUser> records = sysUserPage.getRecords();
        for (SysUser list:records){
            OrderRoles orderRoles = orderRolesService.getOne(new QueryWrapper<OrderRoles>().eq("sor_id",list.getSysId()));
            Role role = roleService.getById(orderRoles.getSorRoid());
            list.setSysRoleId(role.getId());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("sysuserdata",records);
        return Result.success(map);
    }

    /**
     * @description: 删除管理员
     * @Param id:主键ID
     * @return: cn.simbrain.util.Result
     */
    @DeleteMapping("/deletesysuser/{id}")
    public Result deletedsysUser(@PathVariable String id){
        boolean res = sysUserService.removeById(id);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 禁用管理员
     * @Param id: 管理员ID
     * @Param stateCode: 禁用状态
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/stopsysuser/{id}/{stateCode}")
    public Result stopsysUser(@PathVariable String id,
                           @PathVariable Integer stateCode){
        SysUser sysUser = sysUserService.getById(id);
        sysUser.setSysStop(stateCode);
        boolean res = sysUserService.updateById(sysUser);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.INTERFACE_REQUEST_TIMEOUT);
    }

    /**
     * @description: 增加管理员
     * @Param sysUser: 管理员实体
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("addsysuser")
    public Result addsysUser(@RequestBody SysUser sysUser){
        if (sysUser.getSysId() == null || sysUser.getSysEmail() == null || sysUser.getSysPwd() == null)
            return Result.failure(ResultCode.PARAM_NOT_COMPLETE);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("sys_id",sysUser.getSysId().trim());
        SysUser sysUserInSql = sysUserService.getOne(wrapper);
        if (sysUserInSql != null)
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        OrderRoles orderRoles = new OrderRoles();
        orderRoles.setSorId(sysUser.getSysId());
        orderRoles.setSorRoid("5");
        orderRolesService.save(orderRoles);
        boolean res = sysUserService.save(sysUser);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
    }

    /**
     * @description: 查询管理员信息
     * @Param id: 主键ID
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/getsysuser/{id}")
    public Result getsysUser(@PathVariable String id){
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id.trim());
        SysUser sysUser = sysUserService.getOne(wrapper);
        if (sysUser == null)
            return Result.failure(ResultCode.DATA_NONE);
        return Result.success(sysUser);
    }

    /**
     * @description: 更新管理员信息
     * @Param sysUser: 管理员实体
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/updatesysuser")
    public Result updatesysUser(@RequestBody SysUser sysUser){
        SysUser sysUserInSql = sysUserService.getById(sysUser.getId());
        sysUser.setSysId(sysUserInSql.getSysId());
        boolean res = sysUserService.updateById(sysUser);
        if (res)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
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
     * @description: 管理员删除用户
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
     * @description: 管理员新增用户
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
     * @description: 管理员查询某个用户
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
     * @description: 管理员更新用户
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
     * @description: 管理员退出
     * @Param token: jwtt令牌
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/logout")
    public Result sysUserLogOut(){
        return Result.success();
    }

}
