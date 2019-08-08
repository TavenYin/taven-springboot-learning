package com.github.taven.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("match")
public class MatchController {

    @GetMapping("test")
    public Object test() {
        return "OK, u can see it";
    }

    @GetMapping("test/test")
    public Object testtest() {
        return "OK, u can see it";
    }

    @GetMapping("and")
    @PreAuthorize("hasAuthority('sys:user:lock')")
    public Object and() {
        return "OK, u can see it";
    }

}
