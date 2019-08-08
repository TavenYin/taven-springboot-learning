package com.github.taven.web;

import com.github.taven.security.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试方法权限
 */
@RestController
@RequestMapping("api")
public class ApiController {

    // @PreAuthorize 需要通过 @EnableGlobalMethodSecurity 配置开启
    @GetMapping("user")
    @PreAuthorize("hasRole('user')")
    public Object user() {
        User user = SecurityUtils.currentUser();
        return "OK, u can see it. " + user.getUsername();
    }

    @GetMapping("super_admin")
    @PreAuthorize("hasRole('super_admin')")
    public Object super_admin() {
        return "OK, u can see it";
    }

    @GetMapping("user_admin")
    @PreAuthorize("hasRole('user') and hasRole('super_admin')")
    public Object user_admin() {
        return "OK, u can add it";
    }
}
