package cn.simbrain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/3/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartUser {
    /**
     *
     * 用户数量
     */
    private Integer user;
    /**
     *
     * 超级管理员数量
     */
    private Integer admin;
    /**
     *
     * 用户管理员数量
     */
    private Integer adminUser;
    /**
     *
     * 活动管理员数量
     */
    private Integer adminActivity;
    /**
     *
     * 用户和活动管理员数量
     */
    private Integer adminTwo;
    /**
     *
     * 无权限管理员数量
     */
    private Integer adminNull;
    /**
     *
     * 活动数量
     */
    private Integer activityNumber;
}
