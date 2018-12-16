package com.gitee.taven.core.shiro;

import java.io.Serializable;
import java.util.List;

public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 7131512093033017873L;

    private String id;

    private String username;

    private String userType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }
}
