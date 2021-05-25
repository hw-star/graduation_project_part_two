package cn.simbrain.service.impl;

import cn.simbrain.mapper.NoticeMapper;
import cn.simbrain.pojo.Notice;
import cn.simbrain.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/5/25
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
}
