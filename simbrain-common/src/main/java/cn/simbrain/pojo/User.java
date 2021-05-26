package cn.simbrain.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huowei
 * @version 1.0.0
 * @description 用户实体
 * @date 2021/2/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     *
     * 主键 ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     *
     * 用户账号
     */
    @ExcelProperty(index = 0)
    private String userId;
    /**
     *
     * 用户密码
     */
    @ExcelProperty(index = 1)
    private String userPwd;
    /**
     * 用户邮箱
     * 忘记密码使用
     */
    @ExcelProperty(index = 2)
    private String userEmail;
    /**
     *
     * 用户性别
     */
    private Integer userSex;
    /**
     *
     * 用户名字
     */
    @ExcelProperty(index = 3)
    private String userName;
    /**
     *
     * 乐观锁
     */
    @Version
    private Integer version;
    /**
     * 逻辑删除
     * 冻结处理
     */
    @TableLogic
    private Integer deleted;
    /**
     * 用户创建时间
     * 自动填写用户创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date userCreate;
    /**
     * 用户更新时间
     * 自动更新用户修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date userUpdate;
    /**
     *
     * 用户头像
     */
    private String userAvatar;
    /**
     *
     * 用户状态(是否禁用)
     */
    private Integer userStop;
    /**
     *
     * 批量导入普通用户时，性别使用(Mysql中不存在)
     */
    @TableField(exist = false)
    @ExcelProperty(index = 4)
    private String userSexData;
}
