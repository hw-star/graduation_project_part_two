package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @author huowei
 * @version 1.0.0
 * @description 活动实体类
 * @date 2021/2/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    /**
     *
     * 主键 ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     *
     * 活动名称
     */
    private String actName;
    /**
     * 活动创建时间
     * 自动填写活动创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date actCreate;
    /**
     * 活动更新时间
     * 自动更新活动修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date actUpdate;
    /**
     *
     * 活动需求人数
     */
    private Integer actNumber;
    /**
     *
     * 活动描述
     */
    private String actDescription;
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
    /**
     *
     * 是否启用
     */
    private Integer actActive;
}
