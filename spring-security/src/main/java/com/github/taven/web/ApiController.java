package com.github.taven.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

    @GetMapping("user")
    @PreAuthorize("hasRole('user')")
    public Object user() {
        return "OK, u can see it";
    }

    @GetMapping("super_admin")
    @Secured("ROLE_super_admin")
    public Object super_admin() {
        return "OK, u can see it";
    }

}
