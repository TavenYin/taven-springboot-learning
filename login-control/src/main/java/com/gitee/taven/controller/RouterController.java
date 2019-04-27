package com.gitee.taven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {

    @GetMapping("login.html")
    public String login() {
        return "login";
    }

    @GetMapping("index.html")
    public String index() {
        return "index";
    }

    @GetMapping("/")
    public String _login() {
        return "login";
    }
}
