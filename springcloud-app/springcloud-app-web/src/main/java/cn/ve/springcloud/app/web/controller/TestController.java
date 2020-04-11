package cn.ve.springcloud.app.web.controller;

import cn.ve.springcloud.app.user.api.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ve
 * @date 2020/4/8 18:25
 */
@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {

    @Value("${test.aa}")
    private String aa;

    @Autowired
    private UserProvider userProvider;

    @GetMapping("/aa")
    public String aa() {
        return aa;
    }

    @GetMapping("/user")
    public String one() {
        return "restful request success:" + userProvider.one();
    }
}
