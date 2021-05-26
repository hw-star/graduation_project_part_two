package cn.simbrain.pojo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author huowei
 * @version 1.0.0
 * @description 导出活动参加人员名单实体
 * @date 2021/3/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListPerson implements Serializable {
    /**
     *
     * 账号
     */
    @ExcelProperty("账号(手机号)")
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
    @ExcelProperty("邮箱")
    private String email;
}
