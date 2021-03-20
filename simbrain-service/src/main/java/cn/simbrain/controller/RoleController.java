package cn.simbrain.controller;

import cn.simbrain.pojo.OrderRoles;
import cn.simbrain.pojo.Role;
import cn.simbrain.service.OrderRolesService;
import cn.simbrain.service.RoleService;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author huowei
 * @version 1.0.0
 * @description 角色控制层
 * @date 2021/3/19
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private OrderRolesService orderRolesService;

    /**
     * @description: 查询所有角色
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/rolelist")
    public Result getRolesList(){
        List<Role> list = roleService.list();
        if (list == null){
            return Result.failure(ResultCode.DATA_NONE);
        }
        return Result.success(list);
    }

    /**
     * @description: 更改管理员角色
     * @Param orderRoles: 管理员-角色关联实体
     * @return: cn.simbrain.util.Result
     */
    @GetMapping("/updatesysuserrole/{sorId}/{sorRoid}")
    public Result updateSysUserRole(@PathVariable String sorId,
                                    @PathVariable String sorRoid){
        UpdateWrapper<OrderRoles> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("sor_id", sorId);
        OrderRoles roles = new OrderRoles();
        roles.setSorRoid(sorRoid);
        boolean res = orderRolesService.update(roles, updateWrapper);
        if (!res)
            return Result.failure(ResultCode.DATA_WRONG);
        return Result.success();
    }

}
