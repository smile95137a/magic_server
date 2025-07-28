package com.qiyuan.security.config.oauth2;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.qiyuan.security.config.CustomUserDetails;
import com.qiyuan.security.util.JwtTokenUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${app.oauth.redirectUrl}")
	private String redirectUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		String targetUrl = redirectUrl.isEmpty() ? determineTargetUrl(request, response, authentication) : redirectUrl;
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
		String token = jwtTokenUtil.generateToken(user.getUsername());
		targetUrl = UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", token).build().toUriString();
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
