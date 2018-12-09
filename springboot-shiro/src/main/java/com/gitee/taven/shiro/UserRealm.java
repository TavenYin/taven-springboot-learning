package com.gitee.taven.shiro;

import com.gitee.taven.domain.entity.User;
import com.gitee.taven.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User u = userService.getUserByUsername(username);

        if (u == null) {
            throw new UnknownAccountException();
        }

        ByteSource salt = ByteSource.Util.bytes(username);
        // SimpleAuthenticationInfo(Object principal, Object credentials, String realmName)
        // principal 会被封装到 subject 中
        // shiro 默认会把我们的 credentials (也就是password) 和 token 中的作对比，所以我们可以不用做密码校验
        return new SimpleAuthenticationInfo(u, u.getPassword(), salt, getName());
    }

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");
        return info;
    }

}
