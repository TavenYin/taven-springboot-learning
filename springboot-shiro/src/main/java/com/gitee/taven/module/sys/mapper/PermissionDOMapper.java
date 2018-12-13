package com.gitee.taven.module.sys.mapper;

import com.gitee.taven.module.sys.entity.PermissionDO;
import com.gitee.taven.module.sys.entity.PermissionDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PermissionDOMapper {
    int countByExample(PermissionDOExample example);

    int deleteByExample(PermissionDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(PermissionDO record);

    int insertSelective(PermissionDO record);

    List<PermissionDO> selectByExample(PermissionDOExample example);

    PermissionDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PermissionDO record, @Param("example") PermissionDOExample example);

    int updateByExample(@Param("record") PermissionDO record, @Param("example") PermissionDOExample example);

    int updateByPrimaryKeySelective(PermissionDO record);

    int updateByPrimaryKey(PermissionDO record);
}