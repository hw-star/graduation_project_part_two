package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author huowei
 * @version 1.0.0
 * @description 用户实体类
 * @date 2021/2/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     *
     * 主键 ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     *
     * 用户账号
     */
    private String userId;
    /**
     *
     * 用户密码
     */
    private String userPwd;
    /**
     * 用户邮箱
     * 忘记密码使用
     */
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
    private String userName;
    /**
     *
     * 乐观锁
     */
    @Version
    private Integer version;
    /**
     * 逻辑删除
     * 冻结处理(可能)
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
}
