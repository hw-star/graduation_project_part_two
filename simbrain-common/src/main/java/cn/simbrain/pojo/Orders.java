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
 * @description 用户申请活动的信息
 * @date 2021/2/12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders implements Serializable {

    /**
     *
     * 主键 ID
     */
    private Long id;
    /**
     *
     * 申请某活动的用户账号
     */
    @TableField("or_id")
    private Long orId;
    /**
     *
     * 申请某活动的用户名字
     */
    @TableField("or_name")
    private String orName;
    /**
     *
     * 乐观锁
     */
    @Version
    private Integer version;
    /**
     *
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;
}
