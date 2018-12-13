package com.gitee.taven.module.sys.mapper;

import com.gitee.taven.module.sys.entity.UserRoleRefDO;
import com.gitee.taven.module.sys.entity.UserRoleRefDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleRefDOMapper {
    int countByExample(UserRoleRefDOExample example);

    int deleteByExample(UserRoleRefDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserRoleRefDO record);

    int insertSelective(UserRoleRefDO record);

    List<UserRoleRefDO> selectByExample(UserRoleRefDOExample example);

    UserRoleRefDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserRoleRefDO record, @Param("example") UserRoleRefDOExample example);

    int updateByExample(@Param("record") UserRoleRefDO record, @Param("example") UserRoleRefDOExample example);

    int updateByPrimaryKeySelective(UserRoleRefDO record);

    int updateByPrimaryKey(UserRoleRefDO record);
}