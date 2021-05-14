package cn.simbrain.service;

import cn.simbrain.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huowei
 * @version 1.0.0
 * @description 用户服务层
 * @date 2021/2/14
 */
public interface UserService extends IService<User> {
    void saveExcel(MultipartFile file, UserService userService);
}
