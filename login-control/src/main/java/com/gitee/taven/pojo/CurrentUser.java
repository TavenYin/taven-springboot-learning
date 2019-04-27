package com.gitee.taven.pojo;

public class CurrentUser {

    private static final ThreadLocal<UserBO> currentUser = new ThreadLocal<>();

    public static void put(UserBO userBO) {
        currentUser.set(userBO);
    }

    public static UserBO get() {
        return currentUser.get();
    }

}
