package com.qiyuan.web.controller.admin;

import com.qiyuan.web.entity.User;
import com.qiyuan.web.security.RoleExpressions;
import com.qiyuan.web.service.UserAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "後台 - 會員管理", description = "後台使用者/會員管理功能")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService userAdminService;

    @PostMapping("/listAll")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize(RoleExpressions.ONLY_ADMIN)
    @Operation(summary = "查詢全部會員", description = "查詢所有會員，不分條件、不分頁。")
    public List<User> listAll() {
        return userAdminService.findAllUsers();
    }
}
