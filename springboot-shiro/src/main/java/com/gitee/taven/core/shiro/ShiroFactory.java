package com.gitee.taven.core.shiro;

import com.gitee.taven.module.base.Constant;
import com.gitee.taven.module.sys.entity.User;
import com.gitee.taven.module.sys.entity.UserExample;
import com.gitee.taven.module.sys.service.UserService;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiroFactory {

    @Autowired
    private UserService userService;

    public User getUserByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria()
                .andIsDeleteEqualTo(Constant.NOT_DELETE)
                .andUsernameEqualTo(username);
        User user = userService.selectOneByExample(example);

        if (user == null) {
            throw new UnknownAccountException();
        }

        if (Constant.IS_LOCK.equals(user.getIsLock())) {
            throw new LockedAccountException();
        }

        return user;
    }

    /**
     * 构建认证信息
     *
     * @param user
     * @return
     */
    public SimpleAuthenticationInfo buildAuthenticationInfo(User user, String realmName) {
        // ShiroUser 作为实际的 principal
        ShiroUser shiroUser = new ShiroUser();
        BeanUtils.copyProperties(user, shiroUser);

        // SimpleAuthenticationInfo(Object principal, Object credentials, String realmName)
        // principal 会被封装到 subject 中
        // shiro 默认会把我们的 credentials (也就是password) 和 token 中的作对比，所以我们可以不用做密码校验
        ByteSource salt = ByteSource.Util.bytes(user.getUsername());
        return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), salt, realmName);
    }

    public SimpleAuthorizationInfo buildAuthorizationInfo(ShiroUser shiroUser) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

}
