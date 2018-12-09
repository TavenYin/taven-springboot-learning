package com.gitee.taven.sys.domain.bean;

import org.apache.shiro.authc.UsernamePasswordToken;

import javax.validation.constraints.NotBlank;


public class UserBean {

    private Integer id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private boolean rememberMe = false;

    public UsernamePasswordToken createToken() {
        return new UsernamePasswordToken(this.username, this.password, this.rememberMe);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
