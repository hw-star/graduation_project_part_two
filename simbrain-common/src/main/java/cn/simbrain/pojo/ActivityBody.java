package cn.simbrain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/2/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityBody {
    /**
     *
     * 关键字
     */
    private String fuzzyquery;
    /**
     *
     * 开始时间
     */
    private String begin;
    /**
     *
     * 结束时间
     */
    private String end;
}
