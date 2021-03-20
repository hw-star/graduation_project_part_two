package cn.simbrain.pojo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
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
public class ListPerson {
    /**
     *
     * 账号
     */
    @ExcelProperty("账号")
    private String id;
    /**
     *
     * 名字
     */
    @ExcelProperty("名字")
    private String name;
    /**
     *
     * 性别
     */
    @ExcelProperty("性别")
    private String sex;
    /**
     *
     * 联系方式
     */
    @ExcelProperty("联系方式")
    private String phone;

}
