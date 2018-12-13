package com.gitee.taven.module.sys.service;

import com.gitee.taven.core.utils.SqlHelper;
import com.gitee.taven.module.sys.entity.User;
import com.gitee.taven.module.sys.entity.UserDO;
import com.gitee.taven.module.sys.entity.UserDOExample;
import com.gitee.taven.module.sys.mapper.UserDOMapper;
import com.gitee.taven.module.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userMapper;

    @Override
    public UserDO selectOneByExample(UserDOExample example) {
        return SqlHelper.selectOneByExample(userMapper.selectByExample(example));
    }

}
