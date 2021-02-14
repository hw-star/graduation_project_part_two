package cn.simbrain.mapper;

import cn.simbrain.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author huowei
 * @version 1.0.0
 * @description  用户持久层
 * @date 2021/2/10
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
