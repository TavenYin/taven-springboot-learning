package com.gitee.taven.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping("index.html")
    public String index() {
        return "sys/user/index";
    }

}
