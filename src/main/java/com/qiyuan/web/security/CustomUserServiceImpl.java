package com.qiyuan.web.security;


import com.chl.security.enums.RoleType;
import com.chl.security.service.UserService;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dao.UserRoleMapper;
import com.qiyuan.web.dto.UserWithRoles;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.UserRole;
import com.qiyuan.web.entity.example.UserExample;
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
        UserExample e = new UserExample();
        e.createCriteria().andEmailEqualTo(username);

        List<User> users = userMapper.selectByExample(e);
        User user = users.isEmpty() ? null : users.get(0);
        if (user == null) {
            throw new UsernameNotFoundException("無此使用者: " + username);
        }

        List<String> roleIds = userRoleMapper.selectByUserId(user.getId())
                .stream().map(UserRole::getRoleId).collect(Collectors.toList());

        List<RoleType> roleTypes = roleIds.stream()
                .map(RoleType::valueOf)
                .collect(Collectors.toList());

        UserWithRoles userWithRoles = new UserWithRoles();
        userWithRoles.setUser(user);
        userWithRoles.setRoles(roleTypes);

        return new SecurityUser(userWithRoles);
    }
}
