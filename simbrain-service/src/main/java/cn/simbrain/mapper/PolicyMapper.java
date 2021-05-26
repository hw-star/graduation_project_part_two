package cn.simbrain.mapper;

import cn.simbrain.pojo.Policy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author huowei
 * @version 1.0.0
 * @description 政策文件持久层
 * @date 2021/5/22
 */
@Repository
public interface PolicyMapper extends BaseMapper<Policy> {
}
