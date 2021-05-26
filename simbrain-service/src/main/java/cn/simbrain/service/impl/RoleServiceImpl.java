package cn.simbrain.service.impl;

import cn.simbrain.mapper.RoleMapper;
import cn.simbrain.pojo.Role;
import cn.simbrain.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description 角色实现层
 * @date 2021/3/19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
