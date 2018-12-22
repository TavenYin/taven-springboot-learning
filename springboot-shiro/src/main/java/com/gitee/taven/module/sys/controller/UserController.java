package com.gitee.taven.module.sys.controller;

import com.gitee.taven.module.sys.entity.User;
import com.gitee.taven.module.sys.service.RoleService;
import com.gitee.taven.module.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("index.html")
    public String index() {
        return "sys/user/index";
    }

}
