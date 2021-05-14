package cn.simbrain.provide;

import cn.simbrain.pojo.User;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/3/20
 */
public class ExportProvide {

    /**
     * @description: 读取Excel
     * @Param file: Excel
     * @return: void
     */
    public static void readExcel(String file){
        EasyExcel.read(file, User.class, new ReadExcelProvide()).sheet().doRead();
    }

    /**
     * 导出 Excel ：一个 sheet，带表头.
     * @param response  HttpServletResponse
     * @param  data      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param model     映射实体类，Excel 模型
     * @throws Exception 异常
     */
    public static void writeExcel(HttpServletResponse response, List<? extends Object> data, String fileName, String sheetName, Class model) throws Exception {

        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 10);
        // 字体
        headWriteCellStyle.setWriteFont(headWriteFont);
        headWriteCellStyle.setWrapped(true);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        //设置内容靠中对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(getOutputStream(fileName, response),model)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet(sheetName)
                .registerWriteHandler(horizontalCellStyleStrategy)
                //最大长度自适应 目前没有对应算法优化 建议注释掉不用 会出bug
              //.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .doWrite(data);
    }

    /**
     * 导出文件时为Writer生成OutputStream.
     *
     * @param fileName 文件名
     * @param response response
     * @return ""
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment; filename=" +  fileName + ".xlsx");
            return response.getOutputStream();
        } catch (IOException e) {
            throw new Exception("导出excel表格失败!", e);
        }
    }

}
