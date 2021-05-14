package cn.simbrain.provide;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author huowei
 * @version 1.0.0
 * @description druid数据库连接池
 * @date 2021/2/14
 */
@Configuration
public class DruidProvide {

    /**
     * @description: 配置druid的监控web  ip:part/druid
     * PARAM_NAME_USERNAME web的账号
     * PARAM_NAME_PASSWORD web的密码
     * @return: org.springframework.boot.web.servlet.ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");
        Map<String,String> map = new HashMap<>();
        map.put(StatViewServlet.PARAM_NAME_USERNAME,"simbrain");
        map.put(StatViewServlet.PARAM_NAME_PASSWORD,"huowei");
        map.put(StatViewServlet.PARAM_NAME_ALLOW,"");
        map.put(StatViewServlet.PARAM_NAME_DENY,"");
        bean.setInitParameters(map);
        return bean;
    }
}
