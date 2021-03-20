package cn.simbrain.pojo.FindPwd;

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
public class FindPwd {

    /**
     *
     * 账号
     */
    private String id;
    /**
     *
     * 邮箱
     */
    private String email;
}
