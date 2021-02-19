package cn.simbrain.pojo.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/2/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserLogin {
    /**
     *
     * 管理员登录账号
     */
    private String sysUserLoginId;
    /**
     *
     * 管理员登录密码
     */
    private String sysUserLoginPwd;
}
