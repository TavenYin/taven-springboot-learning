package com.gitee.taven.controller;

import com.gitee.taven.pojo.ApiResult;
import com.gitee.taven.pojo.UserDTO;
import com.gitee.taven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public ApiResult login(@RequestBody UserDTO userDTO) {
        return new ApiResult(200, "登录成功", userService.buildUserInfo(userDTO));
    }

    @GetMapping("info")
    public ApiResult info(@RequestHeader("Authorization") String jwt) {
        return new ApiResult(200, null, userService.getUserByToken(jwt));
    }

    @PostMapping("logout")
    public ApiResult logout(@RequestHeader("Authorization") String jwt) {
        userService.logout(jwt);
        return new ApiResult(200, "成功", null);
    }

}

