package com.qiyuan.web.controller.front;

import com.qiyuan.web.dto.request.*;
import com.qiyuan.web.dto.response.*;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.AuthService;
import com.qiyuan.web.service.MemberService;
import com.qiyuan.web.service.OAuth2LoginService;
import com.qiyuan.web.service.OfferingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "前台會員 ", description = "前台會員相關功能")
@RequiredArgsConstructor
public class UserFrontController {

    private final AuthService authService;
    private final OfferingService offeringService;
    private final MemberService memberService;
    private final OAuth2LoginService oAuth2LoginService;



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
    @PreAuthorize(RoleExpressions.ONLY_USER)
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "會員資料修改", description = "需帶 Bearer JWT token")
    public boolean modifyUser(@RequestBody UserProfileModifyRequest req) {
        return authService.modifyUser(req);
    }

    @PostMapping("/info")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    @Operation(summary = "取得會員資料",description = "依據 JWT token 取得當前會員個人資料。")
    public UserProfileResponse getProfile() {
        return authService.getProfile();
    }

    @PostMapping("/record/purchase")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    @Operation(summary = "取得會員消費紀錄", description = "需帶 Bearer JWT token")
    public List<RecordVO> getPurchaseRecord(@RequestBody @Validated RecordPeriodRequest req) {
        return memberService.getPurchaseRecord(req);
    }

    @PostMapping("/record/offering")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    @Operation(summary = "取得會員供奉紀錄", description = "需帶 Bearer JWT token")
    public List<RecordVO> getOfferingRecord(@RequestBody @Validated RecordPeriodRequest req) {
        return offeringService.getOfferingRecord(req);
    }

    @PostMapping("/record/my-god-info")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize(RoleExpressions.ONLY_USER)
    @Operation(summary = "取得我的供奉狀態", description = "需帶 Bearer JWT token")
    public List<MyGodInfoVO> getMyGodInfo() {
        return memberService.getMyGodInfo();
    }


    @PostMapping("/forget-password")
    public boolean sendResetPasswordMail(@RequestBody ForgetPasswordRequest req) {
        memberService.sendResetPasswordMail(req.getEmail());
        return true;
    }

    @GetMapping("/reset-password/verify")
    public boolean verifyResetToken(@RequestParam String token) {
        memberService.verifySsoToken(token);
        return true;
    }

    @PostMapping("/reset-password")
    public boolean resetPassword(@RequestBody ResetPasswordRequest req) {
        return memberService.resetPassword(req);
    }

    @PostMapping("/oauth2/login")
    public OAuthLoginResponse oauth2Login(@RequestBody Oauth2LoginRequest request) {
        return oAuth2LoginService.oauth2Login(
                request.getProvider(),
                request.getOauthId(),
                request.getEmail(),
                request.getNickname()
        );
    }


}
