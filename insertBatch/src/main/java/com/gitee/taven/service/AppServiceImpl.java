package com.gitee.taven.service;

import com.gitee.taven.MyBatisBatchSupport;
import com.gitee.taven.entity.User;
import com.gitee.taven.entity.UserExample;
import com.gitee.taven.mapper.UserMapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 清理测试数据
     */
    @Override
    public void clearData() {
        UserExample example = new UserExample();
        example.createCriteria();
        userMapper.deleteByExample(example);
    }

    /**
     * mybatis 拼接
     *
     * @param userList
     */
    @Override
    @Transactional
    public void mybatisInsert(List<User> userList) {
        userMapper.insertBatch(userList);
    }

    /**
     * mybatis 批处理
     *
     * @param userList
     */
    @Override
    public void mybatisBatch(List<User> userList) {
//        SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//
//        try {
//            for (Object obj : userList) {
//                session.insert("com.gitee.taven.mapper.UserMapper.insert", obj);
//            }
//            session.flushStatements();
//            session.commit();
//            session.clearCache();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            session.rollback();
//
//        } finally {
//            session.close();
//        }
        MyBatisBatchSupport.batchInsert(sqlSessionTemplate.getSqlSessionFactory(),
                "com.gitee.taven.mapper.UserMapper.insert2", userList);
    }

    /**
     * jdbcTemplate 批处理
     *
     * @param userList
     */
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

    /**
     * 原生JDBC
     *
     * @param userList
     * @throws SQLException
     */
    @Override
    public void nativeJdbcBatch(List<User> userList) throws SQLException {
        // 这里直接从连接池里拿一个连接
        Connection connection = jdbcTemplate.getDataSource().getConnection();
        String SQL = "insert into sys_user (id, username, `password`) values (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);

        connection.setAutoCommit(false);

        for (User user : userList) {
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.addBatch();
        }

        try {
            preparedStatement.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();

        } finally {
            connection.close();
        }
    }

}
