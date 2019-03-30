package com.gitee.taven.service;

import com.gitee.taven.entity.User;
import com.gitee.taven.entity.UserExample;
import com.gitee.taven.mapper.UserMapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.Assert.notNull;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public User selectOneByExample(UserExample example) {
        List<User> list = userMapper.selectByExample(example);
        if (list.size() == 1) {
            return list.get(0);
        } else if (list.size() > 1) {
            throw new TooManyResultsException("Expected one result (or null) to be returned by selectOneByExample(), but found: " + list.size());
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void select() {
        userMapper.selectByPrimaryKey("1");
//        userMapper.selectByPrimaryKey("1");
    }

    @Override
    public void selectList() {
        userMapper.selectUserList();
    }

    @Override
    public void batchMode() {
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.REUSE, false);
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.selectUserList();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            // insert update delete 需要commit
            // sqlSession.commit();
            sqlSession.close();

        }

    }

    public Object getList(){
        List<Map> l = new ArrayList<>();
        Map m = new HashMap();
        m.put("name", "taven");
        l.add(m);
        return l;
    }

    public static void main(String args[]) {
        List<User> list = (List<User>) new UserServiceImpl().getList();
        System.out.println(list);
    }

}
