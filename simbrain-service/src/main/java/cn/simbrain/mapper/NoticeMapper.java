package cn.simbrain.mapper;

import cn.simbrain.pojo.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author huowei
 * @version 1.0.0
 * @description 通知公告持久层
 * @date 2021/5/25
 */
@Repository
public interface NoticeMapper extends BaseMapper<Notice> {
}