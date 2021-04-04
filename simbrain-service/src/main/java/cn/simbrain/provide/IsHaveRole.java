package cn.simbrain.provide;

import cn.simbrain.pojo.OrderRoles;
import cn.simbrain.service.OrderRolesService;
import cn.simbrain.util.Jwt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/4/4
 */
public class IsHaveRole {

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
