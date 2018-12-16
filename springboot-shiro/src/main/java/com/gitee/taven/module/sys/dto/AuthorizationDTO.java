package com.gitee.taven.module.sys.dto;


import com.gitee.taven.module.sys.entity.Permission;
import com.gitee.taven.module.sys.entity.Role;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户授权信息 DTO
 */
public class AuthorizationDTO {

    private String userId;

    private Set<Role> roles = new LinkedHashSet<>();

    private Set<Permission> permissions = new LinkedHashSet<>();

    public Set<String> getRoleCodeSet() {
        if (roles.isEmpty()) {
            return new LinkedHashSet<>();
        } else {
            return roles.stream().map(r -> r.getRoleCode()).collect(Collectors.toSet());
        }
    }

    public Set<String> getPermissionCodeSet() {
        if (permissions.isEmpty()) {
            return new LinkedHashSet<>();
        } else {
            return permissions.stream().map(p -> p.getPermissionCode()).collect(Collectors.toSet());
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
