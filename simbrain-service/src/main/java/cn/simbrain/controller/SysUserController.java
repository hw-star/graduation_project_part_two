package cn.simbrain.controller;

import cn.simbrain.mapper.SysUserMapper;
import cn.simbrain.pojo.SysUser;
import cn.simbrain.pojo.User;
import cn.simbrain.service.UserService;
import cn.simbrain.util.Jwt;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huowei
 * @version 1.0.0
 * @description 管理员控制层
 * @date 2021/2/12
 */
@CrossOrigin
@RestController
@RequestMapping("/sysuser")
public class SysUserController {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private Jwt jwt;

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

    @PostMapping("/login")
    public Result sysUserLogin(){
        Map<String,String> map = new HashMap<>();
        map.put("token","admin");
        return Result.success(map);
    }

    @GetMapping("/info")
    public Result getInfo(){
        Map<String,String> map = new HashMap<>();
        map.put("roles","admin");
        map.put("name","admin");
        map.put("avatar","https://youngvolunteer.oss-cn-beijing.aliyuncs.com/common.gif");
        return Result.success(map);
    }

    @GetMapping("userlist/{current}/{limit}")
    public Result getUsersListPage(@PathVariable long current,
                                   @PathVariable long limit,
                                   @RequestParam(value = "fuzzyquery",required = false) String fuzzyquery){
        Page<User> userPage = new Page<>(current,limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!"".equals(fuzzyquery)){
            if ("男".equals(fuzzyquery)){
                wrapper.eq("user_sex",1);
            }else if("女".equals(fuzzyquery)){
                wrapper.eq("user_sex",0);
            }
            wrapper
                    .or().like("user_id",fuzzyquery)
                    .or().like("user_pwd",fuzzyquery)
                    .or().like("user_email",fuzzyquery)
                    .or().like("user_name",fuzzyquery);

        }
        wrapper.orderByDesc("user_create");
        userService.page(userPage,wrapper);
        long total = userPage.getTotal();
        List<User> records = userPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("userdata",records);
        return Result.success(map);
    }


}
