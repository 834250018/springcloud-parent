package cn.ve.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author ve
 * @date 2020/4/7 22:17
 */
@SpringBootApplication
public class SpringcloudGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringcloudGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("blog", r ->
                        r.path("/test.html").uri("http://tieba.baidu.com")) // 匹配路由,转发路由
                .build();
    }

}
