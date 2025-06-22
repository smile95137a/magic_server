package com.qiyuan.web.controller.front;

import com.chl.security.response.ApiResponse;
import com.qiyuan.web.dto.request.RefreshTokenRequest;
import com.qiyuan.web.dto.request.UserLoginRequest;
import com.qiyuan.web.dto.request.UserRegisterRequest;
import com.qiyuan.web.dto.response.LoginResponse;
import com.qiyuan.web.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserFrontController {

    private final AuthService authService;

    public UserFrontController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public boolean register(@RequestBody UserRegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody UserLoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(@RequestBody RefreshTokenRequest req) {
        return authService.refreshToken(req.getRefreshToken());
    }

    @PostMapping("/modify")
    public ApiResponse<?> modify() {
        return null;
    }

//    @PostMapping("/forget-password")
//    public ApiResponse<?> changePassword(@RequestBody ChangePasswordRequest req) {
//        userAppService.changePassword(req.getOldPassword(), req.getNewPassword());
//        return ApiResponse.success("修改成功");
//    }
}
