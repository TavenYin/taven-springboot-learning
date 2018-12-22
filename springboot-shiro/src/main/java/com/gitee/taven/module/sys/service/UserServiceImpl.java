package com.gitee.taven.module.sys.service;

import com.gitee.taven.core.utils.SqlHelper;
import com.gitee.taven.module.base.Constant;
import com.gitee.taven.module.sys.dto.AuthorizationDTO;
import com.gitee.taven.module.sys.entity.User;
import com.gitee.taven.module.sys.entity.UserExample;
import com.gitee.taven.module.sys.mapper.UserMapper;
import com.gitee.taven.module.sys.mapper.UserRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRelationMapper userRelMapper;

    @Override
    public User selectOneByExample(UserExample example) {
        return SqlHelper.selectOneByExample(userMapper.selectByExample(example));
    }

    @Override
    public User getUserByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andIsDeleteEqualTo(Constant.NOT_DELETE)
                .andUsernameEqualTo(username);
        return this.selectOneByExample(example);
    }

    @Override
    public AuthorizationDTO getRolesAndPermissions(String userId) {
        return userRelMapper.getRolesAndPermissions(userId);
    }
}
