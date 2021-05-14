package cn.simbrain.service.impl;

import cn.simbrain.mapper.UserMapper;
import cn.simbrain.pojo.User;
import cn.simbrain.provide.ReadExcelProvide;
import cn.simbrain.service.UserService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/2/18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public void saveExcel(MultipartFile file, UserService userService) {
        try {
            // 文件输入流
            InputStream in = file.getInputStream();
            EasyExcel.read(in, User.class,new ReadExcelProvide(userService)).sheet().doRead();
        }catch (Exception e){

        }
    }
}
