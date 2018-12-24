package com.gitee.taven.module.sys.controller;

import com.gitee.taven.core.shiro.AuthRealm;
import com.gitee.taven.core.shiro.ShiroUser;
import com.gitee.taven.module.sys.bean.UserBean;
import com.gitee.taven.module.base.AjaxResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
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
        Subject subject =SecurityUtils.getSubject();
        // 如果用户 "已经授权" 跳转至首页
        if (subject.isAuthenticated()) {
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

    @GetMapping("main.html")
    public String main() {
        return "main";
    }

    @GetMapping("index.html")
    public ModelAndView getPrincipal(ModelAndView mv) {
        Subject subject = SecurityUtils.getSubject();
        ShiroUser principal = (ShiroUser) subject.getPrincipal();
        mv.setViewName("index");
        mv.addObject("principal", principal.getUsername());
        return mv;
    }

    @GetMapping("clear_shiro_cache")
    @ResponseBody
    public AjaxResult clearShiroCache() {
        Subject subject = SecurityUtils.getSubject();
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        AuthRealm authRealm = (AuthRealm) securityManager.getRealms().iterator().next();
        // shiro 默认用 Principals 作为缓存key
        authRealm.getAuthorizationCache().remove(subject.getPrincipals());
        return AjaxResult.successResponse("clear");
    }

    @GetMapping("admin/has_role")
    @ResponseBody
    public Object hasRole(String role) {
        Subject subject = SecurityUtils.getSubject();
        return subject.hasRole(role) ? "yes" : "no";
    }

    @GetMapping("admin/has_p")
    @ResponseBody
    public Object hasPermission(String p) {
        Subject subject = SecurityUtils.getSubject();
        return subject.isPermitted(p) ? "yes" : "no";
    }

}
