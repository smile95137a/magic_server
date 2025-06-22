package com.qiyuan.web.security;


import com.chl.security.enums.RoleType;
import com.chl.security.service.UserService;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dao.UserRoleMapper;
import com.qiyuan.web.dto.UserWithRoles;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.UserRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserServiceImpl
        implements UserService {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    public CustomUserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectByPrimaryKey(username);
        if (user == null) {
            throw new UsernameNotFoundException("無此使用者: " + username);
        }

        // 查詢角色 ID 列表（如 USER、ADMIN）
        List<String> roleIds = userRoleMapper.selectByUserId(user.getId())
                .stream().map(UserRole::getRoleId).collect(Collectors.toList());

        // 轉換成 enum RoleType（注意可能會拋出 IllegalArgumentException，要處理）
        List<RoleType> roleTypes = roleIds.stream()
                .map(RoleType::valueOf)
                .collect(Collectors.toList());

        // 封裝成 UserWithRoles
        UserWithRoles userWithRoles = new UserWithRoles();
        userWithRoles.setUser(user);
        userWithRoles.setRoles(roleTypes);

        // 傳給 SecurityUser（實作 UserDetails）
        return new SecurityUser(userWithRoles);
    }
}
