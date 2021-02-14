package cn.simbrain.controller;

import cn.simbrain.mapper.SysUserMapper;
import cn.simbrain.pojo.SysUser;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huowei
 * @version 1.0.0
 * @description 管理员控制层
 * @date 2021/2/12
 */

@RestController
public class SysUserController {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * @description: 测试查询单个管理员数据
     * @Param id: ID  主键
     * @return: java.lang.String
     */
    @GetMapping("findsysuser/{id}")
    public Result getSysUser(@PathVariable("id") long id){
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser != null)
            return Result.success(sysUser);
        return Result.failure(ResultCode.USER_LOGIN_ERROR);
    }
}
