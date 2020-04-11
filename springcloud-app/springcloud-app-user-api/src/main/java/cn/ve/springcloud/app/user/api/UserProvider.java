package cn.ve.springcloud.app.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ve
 * @date 2020/4/11 13:37
 */
@FeignClient("springcloud-app-user")
public interface UserProvider {

    @GetMapping("/user/one")
    String one();

}
