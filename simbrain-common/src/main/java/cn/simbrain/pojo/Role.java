package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huowei
 * @version 1.0.0
 * @description 角色实体类
 * @date 2021/3/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    /**
     *
     * 主键 ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     *
     * 角色名字
     */
    private String roleName;
    /**
     *
     * 角色描述
     */
    private String roleDescription;
    /**
     *
     * 角色代码
     */
    private String roleCode;
}
