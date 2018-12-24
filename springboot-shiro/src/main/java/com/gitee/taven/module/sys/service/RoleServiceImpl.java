package com.gitee.taven.module.sys.service;

import com.gitee.taven.core.utils.SqlHelper;
import com.gitee.taven.module.sys.entity.Role;
import com.gitee.taven.module.sys.entity.RoleExample;
import com.gitee.taven.module.sys.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role selectOneByExample(RoleExample example) {
        return SqlHelper.selectOneByExample(roleMapper.selectByExample(example));
    }

}
