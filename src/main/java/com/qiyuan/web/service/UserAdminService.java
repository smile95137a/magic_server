package com.qiyuan.web.service;

import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAdminService {
    private final UserMapper userMapper;

    /** 查詢全部使用者 */
    public List<User> findAllUsers() {
        log.info("[UserAdminService] 查詢全部使用者");
        return userMapper.selectAll();
    }
}
