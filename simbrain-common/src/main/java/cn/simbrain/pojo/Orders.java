package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.*;
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
    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     *
     * 申请某活动的用户账号
     */
    private String orId;
    /**
     *
     * 申请某活动的活动号
     */
    private String orAcid;
    /**
     *
     * 申请某活动的用户名字
     */
    private String orName;
    /**
     *
     * 乐观锁
     */
    @Version
    private Integer version;
    /**
     *
     * 用户邮箱
     */
    private String orEmail;
    /**
     *
     * 活动名字
     */
    private String actName;
}
