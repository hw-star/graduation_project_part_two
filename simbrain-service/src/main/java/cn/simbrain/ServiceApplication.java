package cn.simbrain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author huowei
 * @version 1.0.0
 * @description 具体操作
 * @date 2021/2/11
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.simbrain.mapper")
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class);
    }
}
