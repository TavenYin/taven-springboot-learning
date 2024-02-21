package com.github.taven.springbootvthread;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class GenerateData {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void generateData() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS t_user (id serial primary key, username varchar(255) not null, email varchar(255) not null, mobile varchar(11) not null);";
        jdbcTemplate.execute(createTableSql);

        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 2000000; i++) {
            String username = "user" + i;
            String email = "user" + i + "@example.com";
            String mobile = "138" + String.format("%08d", i);
            users.add(new User(username, email, mobile));
        }

        String sql = "insert into t_user (username, email, mobile) values (?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User user = users.get(i);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getMobile());
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }

}
