package com.gitee.taven.pojo;

public class UserBO {
    private String username;

    private String password;

    private String realName;

    private boolean kickout;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public boolean isKickout() {
        return kickout;
    }

    public void setKickout(boolean kickout) {
        this.kickout = kickout;
    }
}
