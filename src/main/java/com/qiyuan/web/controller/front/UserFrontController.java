package com.qiyuan.web.controller.front;

import com.qiyuan.web.dto.request.RefreshTokenRequest;
import com.qiyuan.web.dto.request.UserLoginRequest;
import com.qiyuan.web.dto.request.UserProfileModifyRequest;
import com.qiyuan.web.dto.request.UserRegisterRequest;
import com.qiyuan.web.dto.response.LoginResponse;
import com.qiyuan.web.dto.response.UserProfileResponse;
import com.qiyuan.web.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "前台會員 ", description = "前台會員相關功能")
public class UserFrontController {

    private final AuthService authService;

    public UserFrontController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "會員註冊", description = "註冊新會員帳號。傳入 email、密碼、手機、暱稱、Line ID、收貨人姓名、收貨地址等資訊。")
    public boolean register(@RequestBody UserRegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("/login")
    @Operation(summary = "會員登入",description = "帳號密碼登入。成功後回傳 accessToken 及 refreshToken。")
    public LoginResponse login(@RequestBody UserLoginRequest req) {
        return authService.login(req);
    }

    @PostMapping("/refresh")
    @Operation(summary = "刷新 Access Token", description = "使用 refreshToken 換取新 accessToken。")
    public LoginResponse refresh(@RequestBody RefreshTokenRequest req) {
        return authService.refreshToken(req.getRefreshToken());
    }

    @PostMapping("/modify")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "會員資料修改", description = "需帶 Bearer JWT token")
    public boolean modifyUser(@RequestBody UserProfileModifyRequest req) {
        return authService.modifyUser(req);
    }

    @PostMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "取得會員資料",description = "依據 JWT token 取得當前會員個人資料。")
    public UserProfileResponse getProfile() {
        return authService.getProfile();
    }

//    @PostMapping("/forget-password")
//    public ApiResponse<?> changePassword(@RequestBody ChangePasswordRequest req) {
//        userAppService.changePassword(req.getOldPassword(), req.getNewPassword());
//        return ApiResponse.success("修改成功");
//    }
}
