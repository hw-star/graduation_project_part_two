package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Field;
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
public class Activity implements Serializable {

    /**
     *
     * 主键 ID
     */
    private Long id;
    /**
     *
     * 活动名称
     */
    @TableField("act_name")
    private String actName;
    /**
     * 活动创建时间
     * 自动填写活动创建时间
     */
    @TableField(value = "act_create",fill = FieldFill.INSERT)
    private Date actCreate;
    /**
     * 活动更新时间
     * 自动更新活动修改时间
     */
    @TableField(value = "act_update",fill = FieldFill.INSERT_UPDATE)
    private Date actUpdate;
    /**
     *
     * 活动需求人数
     */
    @TableField("act_number")
    private Integer actNumber;
    /**
     *
     * 活动已报人数
     */
    @TableField("act_numbered")
    private Integer actNumbered;
    /**
     *
     * 活动描述
     */
    @TableField("act_description")
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
}
