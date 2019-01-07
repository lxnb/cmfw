package com.baizhi.shiro;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Permissions;
import com.baizhi.entity.Role;
import com.baizhi.mapper.AdminMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    private AdminMapper mapper;

    @Override//授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进行授权");
        String primaryPrincipal = (String) principalCollection.getPrimaryPrincipal();
        Admin admin = mapper.queryAdmin(primaryPrincipal);
        if (admin == null) {
            return null;
        } else {
            System.out.println(admin);
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            List<Role> role = admin.getRole();
            List<Permissions> list = null;
            for (Role role1 : role) {//添加角色
                simpleAuthorizationInfo.addRole(role1.getRoleName());
                list = role1.getPermissions();
            }
            for (Permissions permissions : list) {//添加权限
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
            }
            return simpleAuthorizationInfo;

        }

    }

    @Override//认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String principal = (String) authenticationToken.getPrincipal();
        Admin admin = new Admin();
        admin.setName(principal);
        Admin admin1 = mapper.selectOne(admin);
        if (admin1 != null) {
            AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin1.getName(), admin1.getPassword(), ByteSource.Util.bytes(admin1.getSalt()), this.getName());
            return authenticationInfo;
        } else {
            return null;
        }

    }
}
