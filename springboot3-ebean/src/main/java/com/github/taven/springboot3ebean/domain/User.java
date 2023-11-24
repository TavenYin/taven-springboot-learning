package com.github.taven.springboot3ebean.domain;

import com.github.taven.springboot3ebean.domain.finder.UserFinder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="sys_user")
public class User {

    public static final UserFinder userFinder = new UserFinder();

    @Id
    Integer id;

    String username;

}
