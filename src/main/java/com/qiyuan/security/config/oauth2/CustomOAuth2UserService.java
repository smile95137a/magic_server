package com.qiyuan.security.config.oauth2;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.qiyuan.security.config.CustomUserDetails;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dao.UserRoleMapper;
import com.qiyuan.web.entity.User;
import com.qiyuan.web.entity.UserRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        log.info("CustomOAuth2UserService loadUser");
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        return processOAuth2User(oAuth2UserRequest, oAuth2User);
    }

    private CustomUserDetails processOAuth2User(OAuth2UserRequest request, OAuth2User oAuth2User) {
        String clientRegistrationId = request.getClientRegistration().getRegistrationId();

        CustomAbstractOAuth2UserInfo userInfo =
                OAuth2Utils.getOAuth2UserInfo(clientRegistrationId, oAuth2User.getAttributes());

        String username = userInfo.getEmail();
        User user = userMapper.selectByUsername(username);

        if (user == null) {
            user = registerNewOAuthUser(request, userInfo);
        }

        OAuth2Provider registeredProvider = OAuth2Provider.fromString(clientRegistrationId);
        if (!user.getProvider().equals(registeredProvider.name())) {
            String msg = String.format("抱歉，此電子郵件已與 %s 帳戶綁定。請使用 %s 帳戶登錄，而不是 %s。",
                    user.getProvider(), user.getProvider(), registeredProvider);
            throw new InternalAuthenticationServiceException(msg);
        }

        List<UserRole> roles = userRoleMapper.selectByUserId(user.getId());

        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleId()))
                .collect(Collectors.toList());


        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .name(user.getNickname())
                .authorities(authorities)
                .attributes(oAuth2User.getAttributes())
                .build();
    }

    private User registerNewOAuthUser(OAuth2UserRequest request, CustomAbstractOAuth2UserInfo userInfo) {
        String clientRegistrationId = request.getClientRegistration().getRegistrationId();
        OAuth2Provider provider = OAuth2Provider.fromString(clientRegistrationId);

        String userId = UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ROOT);

        User newUser = User.builder()
                .id(userId)
                .username(userInfo.getEmail())
                .email(userInfo.getEmail())
                .nickname(userInfo.getName())
                .provider(provider.name())
                .build();

        userMapper.insert(newUser);

        UserRole userRole = UserRole.builder()
                .userId(userId)
                .roleId("USER")
                .build();

        userRoleMapper.insert(userRole);

        return newUser;
    }
}
