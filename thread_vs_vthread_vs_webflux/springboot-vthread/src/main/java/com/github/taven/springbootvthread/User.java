package com.github.taven.springbootvthread;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String email;
    private String mobile;

    public User() {
    }

    public User(String username, String email, String mobile) {
        this.username = username;
        this.email = email;
        this.mobile = mobile;
    }
}
