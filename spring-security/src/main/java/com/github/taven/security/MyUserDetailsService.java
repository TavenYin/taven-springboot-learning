package com.github.taven.security;

import com.github.taven.entity.RoleDO;
import com.github.taven.entity.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetailsService implements UserDetailsService {
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

        List<RoleDO> roleDOList = jdbcTemplate.query(Sql.selectRolesByUserId, Sql.newParams(user.getId()), new BeanPropertyRowMapper<>(RoleDO.class));

        return User.builder()
                .username(username)
                .password(user.getPassword())
                .roles(roleDOList.stream().map(RoleDO::getRoleCode).collect(Collectors.joining()))
                .build();
    }

}
