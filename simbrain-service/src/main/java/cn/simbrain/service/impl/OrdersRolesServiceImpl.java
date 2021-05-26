package cn.simbrain.service.impl;

import cn.simbrain.mapper.OrderRolesMapper;
import cn.simbrain.pojo.OrderRoles;
import cn.simbrain.service.OrderRolesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description 角色-管理员关联实现层
 * @date 2021/3/19
 */
@Service
public class OrdersRolesServiceImpl extends ServiceImpl<OrderRolesMapper, OrderRoles> implements OrderRolesService {
}
