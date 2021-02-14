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
 * @description 管理员实体类
 * @date 2021/2/12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {

    /**
     *
     * 主键 ID
     */
    private Long id;
    /**
     *
     * 管理员账号
     */
    @TableField("sys_id")
    private Long sysId;
    /**
     *
     * 管理员密码
     */
    @TableField("sys_pwd")
    private String sysPwd;
    /**
     * 管理员邮箱
     * 忘记密码使用(可能)
     */
    @TableField("sys_email")
    private String sysEmail;
    /**
     *
     * 管理员性别
     */
    @TableField("sys_sex")
    private Integer sysSex;
    /**
     *
     * 管理员名字
     */
    @TableField("sys_name")
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
}
