package cn.simbrain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huowei
 * @version 1.0.0
 * @description 管理员首页日志展示实体
 * @date 2021/3/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDataMsg implements Serializable {
    /**
     *
     * 操作人
     */
    private String name;
    /**
     *
     * IP地址
     */
    private String ip;
    /**
     *
     * 发起url
     */
    private String url;
    /**
     *
     * 时间
     */
    private String time;
    /**
     *
     * 账号
     */
    private String id;
}
