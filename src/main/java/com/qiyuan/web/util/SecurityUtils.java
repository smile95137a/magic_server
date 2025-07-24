package com.qiyuan.web.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.qiyuan.security.config.CustomUserDetails;

public class SecurityUtils {

	/** 取得目前登入者 username */
	public static String getCurrentUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	/** 取得 Authentication 物件 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static CustomUserDetails getCurrentUserPrinciple() {
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof CustomUserDetails) {
				return ((CustomUserDetails) principal);
			}
		}
		return null;
	}

}
