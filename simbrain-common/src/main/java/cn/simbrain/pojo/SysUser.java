package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author huowei
 * @version 1.0.0
 * @description 管理员实体类
 * @date 2021/2/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
    /**
     *
     * 主键 ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     *
     * 管理员账号
     */
    private String sysId;
    /**
     *
     * 管理员密码
     */
    private String sysPwd;
    /**
     * 管理员邮箱
     * 忘记密码使用
     */
    private String sysEmail;
    /**
     *
     * 管理员性别
     */
    private Integer sysSex;
    /**
     *
     * 管理员名字
     */
    private String sysName;
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
     * 管理员创建时间
     * 自动填写管理员创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date sysCreate;
    /**
     * 管理员更新时间
     * 自动更新管理员修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date sysUpdate;
    /**
     *
     * 管理员头像
     */
    private String sysAvatar;
}
