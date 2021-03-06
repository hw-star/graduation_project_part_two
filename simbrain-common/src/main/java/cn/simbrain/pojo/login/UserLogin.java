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
public class UserLogin {
    /**
     *
     * 登录账号
     */
    private String userLoginId;
    /**
     *
     * 登录密码
     */
    private String userLoginPwd;
}
