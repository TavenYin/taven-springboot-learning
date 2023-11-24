package com.github.taven.springboot3ebean.config;

import io.ebean.config.CurrentUserProvider;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser implements CurrentUserProvider {

    @Override
    public Object currentUser() {
        return "test";
    }
}