package com.gitee.taven.service;

import com.gitee.taven.entity.User;
import com.gitee.taven.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void mybatisInsert(List<User> userList) {
        userMapper.insertBatch(userList);
    }

    @Override
    @Transactional
    public void jdbcBatch(List<User> userList) {
        String sql = "insert into sys_user (id, username, `password`) values (?,?,?)";
        BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                User user = userList.get(i);
                preparedStatement.setString(1, user.getId());
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.setString(3, user.getPassword());
            }

            @Override
            public int getBatchSize() {
                return userList.size();
            }
        };
        jdbcTemplate.batchUpdate(sql, setter);
    }

    @Override
    @Transactional
    public void jdbcBatch2(List<User> userList) {
        StringBuilder sql = new StringBuilder("insert into sys_user (id, username, `password`) values (?,?,?)");

        for (User user : userList) {

        }


//        jdbcTemplate.batchUpdate(sql, setter);
    }

}
