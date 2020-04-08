package cn.ve.springcloud.app.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ve
 * @date 2020/4/8 18:25
 */
//@RestController
//@RequestMapping("/user")
public class UserController {

    @GetMapping("/user")
    public String one() {
        return "test";
    }
}
