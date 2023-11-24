package com.github.taven.springboot3ebean.service;

import com.github.taven.springboot3ebean.domain.User;
import com.github.taven.springboot3ebean.domain.query.QUser;
import com.github.taven.springboot3ebean.domain.repo.UserRepository;
import io.ebean.Database;
import io.ebean.annotation.Transactional;
import io.ebean.bean.EntityBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private Database database;

    @Autowired
    private UserRepository userRepository;

    public User findOne(String username) {
        return database.find(User.class)
                .where()
                .eq("username", username)
                .findOne();
    }

    public List<User> findAll(String username) {
        return userRepository.findAll();
    }

    public User findOneByEnhance(String username) {
        return new QUser().username.eq(username).findOne();
    }

    @Transactional
    public User save(User user) {
        if (user.getId() != null) {
            // 强制更新，目前没找到更好的办法
            EntityBean entityBean = (EntityBean) user;
            entityBean._ebean_getIntercept().setForceUpdate(true);
        }

        database.save(user);

        if (user.getUsername().equals("rollback")) {
            throw new RuntimeException("boooom!!");
        }
        return user;
    }

}
