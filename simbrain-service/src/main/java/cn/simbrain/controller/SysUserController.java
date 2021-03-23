package cn.simbrain.controller;

import cn.simbrain.pojo.*;
import cn.simbrain.pojo.FindPwd.FindPwd;
import cn.simbrain.pojo.login.SysUserLogin;
import cn.simbrain.provide.EmailProvide;
import cn.simbrain.service.OrderRolesService;
import cn.simbrain.service.RoleService;
import cn.simbrain.service.SysUserService;
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
    private OrderRolesService orderRolesService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private EmailProvide emailProvide;

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
            String token = Jwt.createJwt(sysUser.getId().toString(), sysUserLogin.getSysUserLoginId(), true,sysUser.getSysName());
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
        SysUser sysUser = sysUserService.getById(claims.getId());
        OrderRoles orderRoles = orderRolesService.getOne(new QueryWrapper<OrderRoles>().eq("sor_id", claims.getSubject()));
        roles[0] = orderRoles.getSorRoid();
        map.put("roles", roles);
        map.put("name",sysUser.getSysName());
        map.put("sysUser",sysUser);
        map.put("avatar", sysUser.getSysAvatar());
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
     * @description: 找回密码
     * @Param findPwd: 账号邮箱实体
     * @return: cn.simbrain.util.Result
     */
    @PostMapping("/findpwd")
    public Result findPwd(@RequestBody FindPwd findPwd){
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("sys_id",findPwd.getId()));
        if (sysUser == null)
            return Result.failure(ResultCode.DATA_NONE);
        if (!sysUser.getSysEmail().equals(findPwd.getEmail()))
            return Result.failure(ResultCode.DATA_WRONG);
        boolean sendState = emailProvide.createEmail(true,sysUser.getSysPwd(),findPwd.getId(),findPwd.getEmail());
        if (sendState)
            return Result.success();
        return Result.failure(ResultCode.SYSTEM_INNER_ERROR);

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
