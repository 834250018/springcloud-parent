package cn.ve.springcloud.app.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author ve
 * @date 2020/4/8 18:23
 */
@EnableEurekaClient
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
