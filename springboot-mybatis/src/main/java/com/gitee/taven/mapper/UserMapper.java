package com.gitee.taven.mapper;

import java.util.List;
import java.util.Map;

import com.gitee.taven.entity.User;
import com.gitee.taven.entity.UserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<Map> selectUserList();

    int insertReturnId(User user);

    int insertOrUpdateReturnId(User user);

    int insertBatchReturnId(User user);
}