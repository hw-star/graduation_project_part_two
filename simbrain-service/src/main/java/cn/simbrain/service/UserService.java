package cn.simbrain.service;

import cn.simbrain.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description 用户服务层
 * @date 2021/2/14
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
}
