package com.qiyuan.web.dao;

import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.example.UsersExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    long countByExample(UsersExample example);

    int deleteByExample(UsersExample example);

    int deleteByPrimaryKey(String id);

    int insert(User row);

    int insertSelective(User row);

    User selectByUsername(String selectByUsername);

    List<User> selectByExample(UsersExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") User row, @Param("example") UsersExample example);

    int updateByExample(@Param("row") User row, @Param("example") UsersExample example);

    int updateByPrimaryKeySelective(User row);

    int updateByPrimaryKey(User row);
}