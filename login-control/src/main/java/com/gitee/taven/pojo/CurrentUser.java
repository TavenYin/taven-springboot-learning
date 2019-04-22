package com.gitee.taven.pojo;

public class CurrentUser {

    private static final ThreadLocal<UserDTO> currentUser = new ThreadLocal<>();

    public static void put(UserDTO userDTO) {
        currentUser.set(userDTO);
    }

    public static UserDTO get() {
        return currentUser.get();
    }

}
