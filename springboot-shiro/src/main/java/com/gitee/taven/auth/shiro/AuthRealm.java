package com.gitee.taven.auth.shiro;

import com.gitee.taven.auth.service.AuthService;
import com.gitee.taven.sys.domain.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private AuthService authService;

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
        User u = authService.getUserByUsername(username);

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
//        info.addRole("admin");
        return info;
    }

}
