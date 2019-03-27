package com.gitee.taven.controller;

import com.gitee.taven.aop.NoRepeatSubmit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubmitController {

    @PostMapping("submit")
    @NoRepeatSubmit
    public Object submit() {
        System.out.println("submit start");

        try {
            // 模拟业务场景
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("submit end");

        return "ok";
    }

}
