package com.github.taven.springboot3ebean.domain.repo;

import com.github.taven.springboot3ebean.domain.User;
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BeanRepository<Integer, User> {

    @Autowired
    public UserRepository(Database database) {
        super(User.class, database);
    }

}
