package com.github.taven.springboot3ebean.api;

import com.github.taven.springboot3ebean.domain.User;
import com.github.taven.springboot3ebean.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestApi {

    @Autowired
    private UserService userService;

    @GetMapping("findOneUser")
    public Object findOneUser(String username) {
        return userService.findOne(username);
    }

    @GetMapping("findOneUser2")
    public Object findOneUser2(String username) {
        return userService.finderOne(username);
    }

    @GetMapping("findAllUser")
    public Object findAllUser(String username) {
        return userService.findAll(username);
    }

    @GetMapping("findOneUserByEnhance")
    public Object findOneUserByEnhance(String username) {
        return userService.findOneByEnhance(username);
    }

    @GetMapping("simpleInsertUser")
    public Object simpleInsertUser(String username) {
        User user = new User();
        user.setUsername(username);
        return userService.save(user);
    }

    @GetMapping("simpleUpdateUser")
    public Object simpleUpdateUser(String username, @RequestParam int id) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        return userService.save(user);
    }

}
