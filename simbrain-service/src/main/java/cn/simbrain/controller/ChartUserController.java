package cn.simbrain.controller;

import cn.simbrain.pojo.ChartUser;
import cn.simbrain.pojo.OrderRoles;
import cn.simbrain.pojo.SysUser;
import cn.simbrain.service.OrderRolesService;
import cn.simbrain.service.RoleService;
import cn.simbrain.service.SysUserService;
import cn.simbrain.service.UserService;
import cn.simbrain.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/3/20
 */
@RestController
@RequestMapping("/chartuser")
public class ChartUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private OrderRolesService orderRolesService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/getchart")
    public Result getChartUser(){
        ChartUser chartUser = new ChartUser();
        chartUser.setUser(userService.count());
        int number = roleService.count();
        int[] sysUser = new int[6];
        for (int i = 1; i <=number; i++ ){
            int res = orderRolesService.count(new QueryWrapper<OrderRoles>().eq("sor_roid",i));
            sysUser[i] = res;
        }
        chartUser.setAdmin(sysUser[1]);
        chartUser.setAdminUser(sysUser[2]);
        chartUser.setAdminActivity(sysUser[3]);
        chartUser.setAdminTwo(sysUser[4]);
        chartUser.setAdminNull(sysUser[5]);
        return Result.success(chartUser);
    }
}
