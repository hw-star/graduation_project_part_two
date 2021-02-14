package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huowei
 * @version 1.0.0
 * @description 用户实体类
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
    private Long id;
    /**
     *
     * 用户账号
     */
    @TableField("user_id")
    private Long userId;
    /**
     *
     * 用户密码
     */
    @TableField("user_pwd")
    private String userPwd;
    /**
     * 用户邮箱
     * 忘记密码使用
     */
    @TableField("user_email")
    private String userEmail;
    /**
     *
     * 用户性别
     */
    @TableField("user_sex")
    private Integer userSex;
    /**
     *
     * 用户名字
     */
    @TableField("user_name")
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
}
