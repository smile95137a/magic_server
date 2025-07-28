package com.qiyuan.web.service;

import java.util.Locale;
import java.util.UUID;

import com.qiyuan.security.config.CustomUserDetails;
import com.qiyuan.security.exception.ApiException;
import com.qiyuan.security.service.TokenStorageService;
import com.qiyuan.security.util.JwtTokenUtil;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dao.UserRoleMapper;
import com.qiyuan.web.dto.InvoiceDTO;
import com.qiyuan.web.dto.request.UserLoginRequest;
import com.qiyuan.web.dto.request.UserProfileModifyRequest;
import com.qiyuan.web.dto.request.UserRegisterRequest;
import com.qiyuan.web.dto.response.LoginResponse;
import com.qiyuan.web.dto.response.UserProfileResponse;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.UserRole;
import com.qiyuan.web.util.SecurityUtils;
import com.qiyuan.web.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final TokenStorageService tokenStorageService;


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
                .provider("local")
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
    	Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		CustomUserDetails userDetail = SecurityUtils.getCurrentUserPrinciple();
		String token = jwtTokenUtil.generateToken(userDetail.getUsername());

		User user = userMapper.selectByUsername(userDetail.getUsername());
		
        return LoginResponse.builder()
                .accessToken(token)
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
        if (req.getInvoice() != null) {
            user.setReceipt(JsonUtil.toJson(req.getInvoice()));
        }
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
        resp.setEmail(user.getEmail());
        if (user.getReceipt() != null) {
            resp.setInvoice(JsonUtil.fromJson(user.getReceipt(), InvoiceDTO.class));
        }
        return resp;
    }


}
