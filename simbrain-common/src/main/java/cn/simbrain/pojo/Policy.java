package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huowei
 * @version 1.0.0
 * @description 政策文件实体
 * @date 2021/5/22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy implements Serializable {
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
     *
     * 逻辑删除
     */
    @TableLogic
    private Integer deleted;
    /**
     * 文件创建时间
     * 自动填写文件创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date poCreate;
    /**
     * 文件更新时间
     * 自动更新文件修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date poUpdate;
    /**
     *
     * 文件题目
     */
    private String poTitle;
    /**
     *
     * 文件内容
     */
    private String poContent;
    /**
     *
     * 文件时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date poTime;
    /**
     *
     * 文件来源
     */
    private String poSource;
    /**
     *
     * 是否启用
     */
    private Integer poActive;
}
