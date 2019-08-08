package com.github.taven.service;

import com.github.taven.entity.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {
    private static final String ROLE_PREFIX = "ROLE_";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO user = null;
        try {
            user = jdbcTemplate.queryForObject(Sql.loadUserByUsername, Sql.newParams(username), new BeanPropertyRowMapper<>(UserDO.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        if (user == null)
            throw new UsernameNotFoundException("用户不存在：" + username);

        return User.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(
                        ROLE_PREFIX +"super_admin",
                        ROLE_PREFIX +"user",
                        "sys:user:add", "sys:user:edit", "sys:user:del",
                        "sys:match", "sys:mm"
                )// 这里偷懒写死几个权限和角色
                .build();
    }

}
