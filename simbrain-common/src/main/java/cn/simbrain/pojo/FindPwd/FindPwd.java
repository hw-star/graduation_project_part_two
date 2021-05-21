package cn.simbrain.pojo.FindPwd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/3/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindPwd implements Serializable {

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
