package com.gitee.taven.module.sys.mapper;

import com.gitee.taven.module.sys.entity.RolePermissionRefDO;
import com.gitee.taven.module.sys.entity.RolePermissionRefDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionRefDOMapper {
    int countByExample(RolePermissionRefDOExample example);

    int deleteByExample(RolePermissionRefDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(RolePermissionRefDO record);

    int insertSelective(RolePermissionRefDO record);

    List<RolePermissionRefDO> selectByExample(RolePermissionRefDOExample example);

    RolePermissionRefDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RolePermissionRefDO record, @Param("example") RolePermissionRefDOExample example);

    int updateByExample(@Param("record") RolePermissionRefDO record, @Param("example") RolePermissionRefDOExample example);

    int updateByPrimaryKeySelective(RolePermissionRefDO record);

    int updateByPrimaryKey(RolePermissionRefDO record);
}