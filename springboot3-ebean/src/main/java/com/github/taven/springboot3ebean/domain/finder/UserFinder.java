package com.github.taven.springboot3ebean.domain.finder;

import com.github.taven.springboot3ebean.domain.User;
import io.ebean.Finder;

public class UserFinder extends Finder<Integer, User> {
    public UserFinder() {
        super(User.class);
    }
}
