package com.gitee.taven.module.auth.controller;

import com.gitee.taven.module.auth.sys.domain.bean.UserBean;
import com.gitee.taven.module.sys.domain.dto.AjaxResult;
import com.gitee.taven.module.sys.domain.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("login.html")
    public String login() {
        // 如果用户 "已经登录" 或者 "记住我状态" 则去首页
        Subject subject =SecurityUtils.getSubject();
        if (subject.isAuthenticated() || subject.isRemembered()) {
            return "redirect:index.html";
        }
        return "login";
    }

    @PostMapping("login")
    @ResponseBody
    public AjaxResult login(@RequestBody @Valid UserBean user) {
        Subject subject = SecurityUtils.getSubject();
        try {
            if (!subject.isAuthenticated()) {
                subject.login(user.createToken());
            }

        } catch (UnknownAccountException e) {
            log.error("用户不存在", e);
            return AjaxResult.failResponse("用户不存在");

        } catch (IncorrectCredentialsException e) {
            log.error("用户名或密码错误", e);
            return AjaxResult.failResponse("用户名或密码错误");

        } catch (LockedAccountException e) {
            log.error("账号已被锁定，请联系管理员", e);
            return AjaxResult.failResponse("账号已被锁定，请联系管理员");

        } catch (ExcessiveAttemptsException e) {
            log.error("操作过于频繁请稍后再试", e);

        } catch (AuthenticationException e) {
            log.error("未知错误", e);
            return AjaxResult.failResponse("未知错误");

        }
        return AjaxResult.successResponse("登陆成功");
    }

    @GetMapping("logout")
    public String  logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:login.html";
    }

    @GetMapping("index.html")
    public ModelAndView getPrincipal(ModelAndView mv) {
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        mv.setViewName("index");
        mv.addObject("principal", principal.getUsername());
        return mv;
    }

    @GetMapping("admin/has_role")
    @ResponseBody
    public Object hasRole(String role) {
        Subject subject = SecurityUtils.getSubject();
        return subject.hasRole(role) ? "yes" : "no";
    }

}
