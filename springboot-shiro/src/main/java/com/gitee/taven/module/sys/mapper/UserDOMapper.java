package com.gitee.taven.module.sys.mapper;

import com.gitee.taven.module.sys.entity.UserDO;
import com.gitee.taven.module.sys.entity.UserDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDOMapper {
    int countByExample(UserDOExample example);

    int deleteByExample(UserDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    List<UserDO> selectByExample(UserDOExample example);

    UserDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") UserDO record, @Param("example") UserDOExample example);

    int updateByExample(@Param("record") UserDO record, @Param("example") UserDOExample example);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);
}