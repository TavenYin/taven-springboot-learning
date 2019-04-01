package com.gitee.taven.controller;

import com.gitee.taven.ApiResult;
import com.gitee.taven.aop.NoRepeatSubmit;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubmitController {

    @PostMapping("submit")
    @NoRepeatSubmit(lockTime = 30)
    public Object submit(@RequestBody UserBean userBean) {
        try {
            // 模拟业务场景
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ApiResult(200, "成功", userBean.userId);
    }

    public static class UserBean {
        private String userId;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId == null ? null : userId.trim();
        }
    }

}
