package cn.ve.springcloud.app.web.controller;

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
@RequestMapping("/user")
@RefreshScope
public class UserController {

    @Value("${test.aa}")
    private String aa;

    @GetMapping("/user")
    public String one() {
        return aa;
    }
}
