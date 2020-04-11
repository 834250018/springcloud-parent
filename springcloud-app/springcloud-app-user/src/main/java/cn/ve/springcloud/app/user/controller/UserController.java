package cn.ve.springcloud.app.user.controller;

import cn.ve.springcloud.app.user.api.UserProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ve
 * @date 2020/4/8 18:25
 */
@RestController
@RequestMapping("/user")
public class UserController implements UserProvider {

    @Override
    @GetMapping("/one")
    public String one() {
        return "get a user!";
    }
}
