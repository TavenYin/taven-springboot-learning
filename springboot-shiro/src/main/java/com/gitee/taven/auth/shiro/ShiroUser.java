package com.gitee.taven.auth.shiro;

import java.io.Serializable;
import java.util.List;

public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 7131512093033017873L;

    private String id;

    private String username;

    private List<String> roles;

    private List<String> permissions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
