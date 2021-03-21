package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huowei
 * @version 1.0.0
 * @description 日志信息  操作成功
 * @date 2021/2/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogFailure {
        /**
         *
         * 开始时间
         */
        private String startTime;
        /**
         *
         * 请求地址
         */
        private String requestUrl;
        /**
         *
         * 请求方式
         */
        private String requestMethod;
        /**
         *
         * 请求IP
         */
        private String requestIp;
        /**
         *
         * 请求方法
         */
        private String requestSignature;
        /**
         *
         * 请求参数
         */
        private String requestParam;
        /**
         *
         * 请求浏览器
         */
        private String requestBrowser;
        /**
         *
         * 请求操作系统
         */
        private String requestSystem;

        /**
         *
         * 主键 ID
         */
        @TableId(type = IdType.INPUT)
        private Long id;
        /**
         *
         * 异常时间
         */
        private String errorTime;
        /**
         *
         * 异常信息
         */
        private  String errorMessage;
        /**
         *
         * 操作人
         */
        private String requestName;
}
