package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author huowei
 * @version 1.0.0
 * @description 志愿风采实体
 * @date 2021/5/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Elegant {
    /**
     *
     * 主键 ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     *
     * 乐观锁
     */
    @Version
    private Integer version;
    /**
     * 逻辑删除
     *
     */
    @TableLogic
    private Integer deleted;
    /**
     * 志愿风采创建时间
     * 自动填写志愿风采创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date elCreate;
    /**
     * 志愿风采更新时间
     * 自动更新志愿风采修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date elUpdate;
    /**
     *
     * 志愿风采人物名字
     */
    private String elName;
    /**
     *
     * 志愿风采头像
     */
    private String elAvatar;
    /**
     *
     * 志愿风采时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date elTime;
    /**
     *
     * 是否启用
     */
    private Integer elActive;
}
