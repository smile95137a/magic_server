package com.qiyuan.security.dao;

import com.qiyuan.security.entity.Token;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapper {
    void insert(Token token);
    Token findByToken(String token);
    int countRefreshByUsername(String username);
    void deleteExpiredTokens();
    boolean revokeToken(String token);
    boolean revokeRefreshByUsername(String username);
    int countTokenExpiredOver7Day();
    void deleteSsoTokensByUsername(String username);

}
