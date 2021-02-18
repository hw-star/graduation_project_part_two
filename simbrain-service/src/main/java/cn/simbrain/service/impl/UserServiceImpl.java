package cn.simbrain.service.impl;

import cn.simbrain.mapper.UserMapper;
import cn.simbrain.pojo.User;
import cn.simbrain.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/2/18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
