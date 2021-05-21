package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/3/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRoles implements Serializable {
    /**
     *
     * 主键 ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     *
     * 管理员账号
     */
    private String sorId;
    /**
     *
     * 乐观锁
     */
    @Version
    private Integer version;
    /**
     *
     * 角色号
     */
    private String sorRoid;
}
