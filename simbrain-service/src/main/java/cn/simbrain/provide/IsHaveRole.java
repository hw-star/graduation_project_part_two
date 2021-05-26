package cn.simbrain.provide;

import cn.simbrain.pojo.OrderRoles;
import cn.simbrain.service.OrderRolesService;
import cn.simbrain.util.Jwt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huowei
 * @version 1.0.0
 * @description 校验角色
 * @date 2021/4/4
 */
public class IsHaveRole {

    /**
     * @description: 检验是否拥有某角色
     * @Param request: 请求
     * @Param items: 角色对应code
     * @Param orderRolesService: 角色-管理员关联
     * @return: boolean
     */
    public static boolean isHave(HttpServletRequest request, String[] items, OrderRolesService orderRolesService){
        Claims claims = Jwt.parseJwt(request.getHeader("X-Token"));
        String id = claims.getSubject();
        OrderRoles orderRoles = orderRolesService.getOne(new QueryWrapper<OrderRoles>().eq("sor_id", id));
        if (orderRoles == null){
            return true;
        }
        String item = orderRoles.getSorRoid();
        for (String every:items) {
            if (every.equals(item))
                return true;
        }
        return false;
    }
}
