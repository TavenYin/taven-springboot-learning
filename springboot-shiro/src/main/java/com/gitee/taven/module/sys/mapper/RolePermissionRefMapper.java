package com.gitee.taven.module.sys.mapper;

import com.gitee.taven.module.sys.entity.RolePermissionRef;
import com.gitee.taven.module.sys.entity.RolePermissionRefExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RolePermissionRefMapper {
    int countByExample(RolePermissionRefExample example);

    int deleteByExample(RolePermissionRefExample example);

    int deleteByPrimaryKey(String id);

    int insert(RolePermissionRef record);

    int insertSelective(RolePermissionRef record);

    List<RolePermissionRef> selectByExample(RolePermissionRefExample example);

    RolePermissionRef selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") RolePermissionRef record, @Param("example") RolePermissionRefExample example);

    int updateByExample(@Param("record") RolePermissionRef record, @Param("example") RolePermissionRefExample example);

    int updateByPrimaryKeySelective(RolePermissionRef record);

    int updateByPrimaryKey(RolePermissionRef record);
}