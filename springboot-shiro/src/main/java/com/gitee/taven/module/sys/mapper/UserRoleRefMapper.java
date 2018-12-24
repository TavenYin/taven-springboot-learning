package com.gitee.taven.module.sys.mapper;

import com.gitee.taven.module.sys.entity.UserRoleRef;
import com.gitee.taven.module.sys.entity.UserRoleRefExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleRefMapper {
    int countByExample(UserRoleRefExample example);

    int deleteByExample(UserRoleRefExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserRoleRef record);

    int insertSelective(UserRoleRef record);

    List<UserRoleRef> selectByExample(UserRoleRefExample example);

    UserRoleRef selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserRoleRef record, @Param("example") UserRoleRefExample example);

    int updateByExample(@Param("record") UserRoleRef record, @Param("example") UserRoleRefExample example);

    int updateByPrimaryKeySelective(UserRoleRef record);

    int updateByPrimaryKey(UserRoleRef record);
}