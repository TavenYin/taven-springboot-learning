package com.github.taven.webfluxfirstexp.service;

import com.github.taven.webfluxfirstexp.dao.UserDao;
import com.github.taven.webfluxfirstexp.model.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public Mono<UserDO> getUser(Long id) {
        return userDao.getModel(id);
    }

}
