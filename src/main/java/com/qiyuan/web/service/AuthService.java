package com.qiyuan.web.service;

import com.qiyuan.security.exception.ApiException;
import com.qiyuan.security.service.TokenStorageService;
import com.qiyuan.security.service.UserService;
import com.qiyuan.security.util.JwtTokenUtil;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dao.UserRoleMapper;
import com.qiyuan.web.dto.request.UserLoginRequest;
import com.qiyuan.web.dto.request.UserProfileModifyRequest;
import com.qiyuan.web.dto.request.UserRegisterRequest;
import com.qiyuan.web.dto.response.LoginResponse;
import com.qiyuan.web.dto.response.UserProfileResponse;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.UserRole;
import com.qiyuan.web.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final TokenStorageService tokenStorageService;

    public AuthService(UserService userService,
                       UserRoleMapper userRoleMapper,
                       PasswordEncoder passwordEncoder,
                       JwtTokenUtil jwtTokenUtil,
                       UserMapper userMapper,
                       TokenStorageService tokenStorageService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userMapper = userMapper;
        this.tokenStorageService = tokenStorageService;
        this.userRoleMapper = userRoleMapper;
    }

    /**
     * 使用者註冊
     */
    @Transactional
    public boolean register(UserRegisterRequest req) {
        // 檢查帳號重複
        User exist = userMapper.selectByUsername(req.getEmail());
        if (exist != null) {
            throw new ApiException("帳號已存在");
        }

        String userId = UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ROOT);

        if (StringUtils.isAnyBlank(req.getEmail(), req.getLineId(), req.getPhone(), req.getPassword(), req.getNickName())) {
            throw new ApiException("信箱、手機、密碼、暱稱、Line Id不可為空");
        }

        User newUser = User.builder()
                .id(userId)
                .username(req.getEmail())
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .phone(req.getPhone())
                .nickname(req.getNickName())
                .addressName(req.getAddressName())
                .lineId(req.getLineId())
                .city(req.getCity())
                .district(req.getArea())
                .address(req.getAddress())
                .build();

        UserRole userRole = UserRole
                .builder()
                .roleId("USER")
                .userId(userId)
                .build();

        if (userMapper.insertSelective(newUser) == 0 || userRoleMapper.insertSelective(userRole) == 0) {
            throw new ApiException("系統發生錯誤，請聯繫客服！");
        }
        return true;
    }

    public LoginResponse login(UserLoginRequest req) {
        UserDetails userDetails = null;
        try {
            userDetails = userService.loadUserByUsername(req.getUsername());
            if (!passwordEncoder.matches(req.getPassword(), userDetails.getPassword())) {
                throw new ApiException("帳號或密碼錯誤");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ApiException("帳號或密碼錯誤");
        }


        String accessToken = jwtTokenUtil.generateToken(userDetails.getUsername());
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails.getUsername());

        tokenStorageService.cleanupExpiredTokens();
        Date expiration = jwtTokenUtil.getExpirationFromToken(refreshToken);
        tokenStorageService.revokeTokensByUsername(userDetails.getUsername());
        tokenStorageService.saveRefreshToken(userDetails.getUsername(), refreshToken, DateUtil.toLocalDateTime(expiration));
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public LoginResponse refreshToken(String refreshToken) {
        if (!tokenStorageService.isTokenValid(refreshToken) || !jwtTokenUtil.isRefreshToken(refreshToken)) {
            throw new ApiException("Refresh Token 無效或已過期");
        }

        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);

        String newAccessToken = jwtTokenUtil.generateToken(username);
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(username);

        tokenStorageService.revokeToken(refreshToken);

        Date expiration = jwtTokenUtil.getExpirationFromToken(newRefreshToken);
        tokenStorageService.saveRefreshToken(username, newRefreshToken, DateUtil.toLocalDateTime(expiration));

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public boolean modifyUser(UserProfileModifyRequest req) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.selectByUsername(username);
        if (user == null) return false;
        user.setNickname(req.getNickName());
        user.setAddressName(req.getAddressName());
        user.setLineId(req.getLineId());
        user.setPhone(req.getPhone());
        user.setCity(req.getCity());
        user.setDistrict(req.getArea());
        user.setAddress(req.getAddress());
        return userMapper.updateByPrimaryKeySelective(user) > 0;
    }

    public UserProfileResponse getProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.selectByUsername(username);
        if (user == null) throw new ApiException("查無會員資料");

        UserProfileResponse resp = new UserProfileResponse();
        resp.setNickName(user.getNickname());
        resp.setAddressName(user.getAddressName());
        resp.setLineId(user.getLineId());
        resp.setPhone(user.getPhone());
        resp.setCity(user.getCity());
        resp.setArea(user.getDistrict());
        resp.setAddress(user.getAddress());
        return resp;
    }


}
