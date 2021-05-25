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
 * @description TODO
 * @date 2021/5/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
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
     * 通知公告创建时间
     * 自动填写通知公告创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date noCreate;
    /**
     * 通知公告更新时间
     * 自动更新通知公告修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date noUpdate;
    /**
     *
     * 通知公告标题
     */
    private String noTitle;
    /**
     *
     * 通知公告内容
     */
    private String noContent;
    /**
     *
     * 通知公告时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date noTime;
    /**
     *
     * 是否启用
     */
    private Integer noActive;
}
