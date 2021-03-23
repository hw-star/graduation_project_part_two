package cn.simbrain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/3/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogDataMsg {
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
