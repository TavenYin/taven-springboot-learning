package com.gitee.taven.module.sys.mapper;

import com.gitee.taven.module.sys.entity.RoleDO;
import com.gitee.taven.module.sys.entity.RoleDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleDOMapper {
    int countByExample(RoleDOExample example);

    int deleteByExample(RoleDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(RoleDO record);

    int insertSelective(RoleDO record);

    List<RoleDO> selectByExample(RoleDOExample example);

    RoleDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RoleDO record, @Param("example") RoleDOExample example);

    int updateByExample(@Param("record") RoleDO record, @Param("example") RoleDOExample example);

    int updateByPrimaryKeySelective(RoleDO record);

    int updateByPrimaryKey(RoleDO record);
}