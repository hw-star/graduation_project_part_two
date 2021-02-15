package cn.simbrain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("log_failure")
public class LogFailure {
        /**
         *
         * 开始时间
         */
        @TableField("start_time")
        private String startTime;
        /**
         *
         * 请求地址
         */
        @TableField("request_url")
        private String requestUrl;
        /**
         *
         * 请求方式
         */
        @TableField("request_method")
        private String requestMethod;
        /**
         *
         * 请求IP
         */
        @TableField("request_ip")
        private String requestIp;
        /**
         *
         * 请求方法
         */
        @TableField("request_signature")
        private String requestSignature;
        /**
         *
         * 请求参数
         */
        @TableField("request_param")
        private String requestParam;
        /**
         *
         * 请求浏览器
         */
        @TableField("request_browser")
        private String requestBrowser;
        /**
         *
         * 请求操作系统
         */
        @TableField("request_system")
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
        @TableField("error_time")
        private String errorTime;
        /**
         *
         * 异常信息
         */
        @TableField("error_message")
        private  String errorMessage;
}
