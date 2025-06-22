package com.qiyuan.web.service;

import com.chl.security.service.TokenStorageService;
import com.chl.security.service.UserService;
import com.chl.security.service.impl.DatabaseTokenStorageService;
import com.chl.security.util.JwtTokenUtil;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dto.request.UserLoginRequest;
import com.qiyuan.web.dto.request.UserRegisterRequest;
import com.qiyuan.web.dto.response.LoginResponse;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.example.UserExample;
import com.qiyuan.web.exception.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final TokenStorageService tokenStorageService;

    public AuthService(UserService userService,
                       PasswordEncoder passwordEncoder,
                       JwtTokenUtil jwtTokenUtil,
                       UserMapper userMapper,
                       TokenStorageService tokenStorageService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userMapper = userMapper;
        this.tokenStorageService = tokenStorageService;
    }

    /**
     * 使用者註冊
     */
    public boolean register(UserRegisterRequest req) {
        // 檢查帳號重複
        User exist = getUserByEmail(req.getEmail());
        if (exist != null) {
            throw new ApiException("帳號已存在");
        }

        User newUser = User.builder()
                .id(UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ROOT))
                .email(req.getEmail())
                .password(req.getPassword())
                .phone(req.getPhone())
                .nickname(req.getNickName())
                .lineId(req.getLineId())
                .build();

        if (StringUtils.isNotBlank(req.getAddressName())) {
            newUser.setAddressName(req.getAddressName());
        }

        if (StringUtils.isNotBlank(req.getAddress())) {
            newUser.setAddress(req.getAddress());
        }

        return userMapper.insertSelective(newUser) > 0;
    }

    public LoginResponse login(UserLoginRequest req) {
        UserDetails userDetails = userService.loadUserByUsername(req.getUsername());
        if (!passwordEncoder.matches(req.getPassword(), userDetails.getPassword())) {
            throw new ApiException("帳號或密碼錯誤");
        }

        String accessToken = jwtTokenUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails.getUsername());

        tokenStorageService.cleanupExpiredTokens();
        Date expiration = jwtTokenUtil.getExpirationFromToken(refreshToken);
        tokenStorageService.revokeTokensByUsername(userDetails.getUsername());
        tokenStorageService.saveRefreshToken(userDetails.getUsername(), refreshToken, expiration.toInstant());
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public LoginResponse refreshToken(String refreshToken) {
        // 驗證 refresh token 格式與有效性
        if (!tokenStorageService.isTokenValid(refreshToken) || !jwtTokenUtil.isRefreshToken(refreshToken)) {
            throw new ApiException("Refresh Token 無效或已過期");
        }

        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);

        String newAccessToken = jwtTokenUtil.generateToken(username);
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(username);

        tokenStorageService.revokeToken(refreshToken);

        Date expiration = jwtTokenUtil.getExpirationFromToken(newRefreshToken);
        tokenStorageService.saveRefreshToken(username, newRefreshToken, expiration.toInstant());

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }


    public User getUserByEmail(String userEmail) {
        UserExample e = new UserExample();
        e.createCriteria().andEmailEqualTo(userEmail);

        List<User> users = userMapper.selectByExample(e);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

}
