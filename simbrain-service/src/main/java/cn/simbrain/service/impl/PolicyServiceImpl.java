package cn.simbrain.service.impl;

import cn.simbrain.mapper.PolicyMapper;
import cn.simbrain.pojo.Policy;
import cn.simbrain.service.PolicyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/5/22
 */
@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements PolicyService {
}
