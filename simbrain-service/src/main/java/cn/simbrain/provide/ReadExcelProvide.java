package cn.simbrain.provide;

import cn.simbrain.pojo.User;
import cn.simbrain.service.UserService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.*;

/**
 * @author huowei
 * @version 1.0.0
 * @description 读excel监听器
 * @date 2021/5/14
 */
public class ReadExcelProvide extends AnalysisEventListener<User> {

    public UserService userService;

    public ReadExcelProvide(){
    }
    public ReadExcelProvide(UserService userService){
        this.userService = userService;
    }

    /**
     * @description: 读 Excel
     * @Param readPerson: 读实体
     * @Param analysisContext: 内容
     * @return: void
     */
    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        if (user == null){
            try {
                throw new ErrorException("数据为空");
            } catch (ErrorException e) {
            }
        }
        if (this.existOneData(userService,user.getUserId()) != null){
            return;
        }
        if ("男".equals(user.getUserSexData())){
            user.setUserSex(1);
        }else if ("女".equals(user.getUserSexData())){
            user.setUserSex(0);
        }else {
            return;
        }
        userService.save(user);
    }

    /**
     * @description: 判断该账号是否存在
     * @Param userService: user服务
     * @Param userId: 账号
     * @return: cn.simbrain.pojo.User
     */
    private User existOneData(UserService userService,String userId){
        return userService.getOne(new QueryWrapper<User>().eq("user_id",userId));
    }

    /**
     * @description: 读取 Excel表头信息
     * @Param headMap: 表头信息
     * @Param context: 内容
     * @return: void
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println(headMap);
    }

    /**
     * @description: DOTO 读取完后执行
     * @Param analysisContext: 内容
     * @return: void
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
