package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.security.util.JwtTokenUtil;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dto.response.OAuthLoginResponse;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.util.RandomGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class OAuth2LoginService {
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;

    public OAuth2LoginService(UserMapper userMapper, JwtTokenUtil jwtTokenUtil) {
        this.userMapper = userMapper;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public OAuthLoginResponse oauth2Login(String provider, String oauthId, String email, String nickname) {
        User user = userMapper.selectByUsername(email);
        boolean needCompleteProfile = false;

        if (user == null) {
            // 第一次登入，自動註冊
            user = new User();
            user.setId(RandomGenerator.getUUID());
            user.setUsername(email);
            user.setEmail(email);
            user.setOauthType(provider);
            user.setOauthId(oauthId);
            user.setNickname(nickname);
            userMapper.insertSelective(user);
            needCompleteProfile = true;
        } else {
            if (user.getOauthType() == null) {
                throw new ApiException("此 email 已被其他方式註冊");
            } else if (!provider.equals(user.getOauthType()) || !oauthId.equals(user.getOauthId())) {
                throw new ApiException("此 email 已被其他方式註冊");
            }

            if (StringUtils.isAnyBlank(user.getPhone(), user.getLineId(), user.getNickname())) {
                needCompleteProfile = true;
            }
        }


        // 產生 JWT
        String token = jwtTokenUtil.generateToken(email);
        return new OAuthLoginResponse(token, needCompleteProfile, user.getEmail(), user.getNickname());
    }
}

