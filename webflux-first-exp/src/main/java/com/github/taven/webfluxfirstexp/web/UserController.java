package com.github.taven.webfluxfirstexp.web;

import com.github.taven.webfluxfirstexp.model.UserDO;
import com.github.taven.webfluxfirstexp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("hi")
    public Mono<String> hi() {
        return Mono.just("hi");
    }

    @GetMapping("user/{id}")
    public Mono<UserDO> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

}
