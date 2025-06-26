package com.qiyuan.web.dto;

import com.qiyuan.security.enums.RoleType;
import com.qiyuan.web.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserWithRoles {
    private User user;
    private List<RoleType> roles;

}

