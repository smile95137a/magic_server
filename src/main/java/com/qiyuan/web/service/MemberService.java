package com.qiyuan.web.service;

import com.qiyuan.security.dao.TokenMapper;
import com.qiyuan.security.entity.Token;
import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.LanternPurchaseMapper;
import com.qiyuan.web.dao.OfferingPurchaseMapper;
import com.qiyuan.web.dao.UserMapper;
import com.qiyuan.web.dto.request.RecordPeriodRequest;
import com.qiyuan.web.dto.response.RecordVO;
import com.qiyuan.web.entity.*;
import com.qiyuan.web.entity.example.UserExample;
import com.qiyuan.web.enums.RecordItem;
import com.qiyuan.web.enums.TokenType;
import com.qiyuan.web.util.DateUtil;
import com.qiyuan.web.util.RandomGenerator;
import com.qiyuan.web.util.SecurityUtils;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MemberService {

    private final OfferingPurchaseMapper offeringPurchaseMapper;
    private final LanternPurchaseMapper lanternPurchaseMapper;
    private final UserMapper userMapper;
    private final TokenMapper tokenMapper;
    private final EmailService emailService;

    @Value("${sso.front-url}")
    private String frontUrl;

    @Value("${sso.expired-minute}")
    private Integer expiredMinute;

    public MemberService(OfferingPurchaseMapper offeringPurchaseMapper, LanternPurchaseMapper lanternPurchaseMapper, UserMapper userMapper, TokenMapper tokenMapper, EmailService emailService) {
        this.offeringPurchaseMapper = offeringPurchaseMapper;
        this.lanternPurchaseMapper = lanternPurchaseMapper;
        this.userMapper = userMapper;
        this.tokenMapper = tokenMapper;
        this.emailService = emailService;
    }

    public List<RecordVO> getPurchaseRecord(RecordPeriodRequest req) {
        Date startTime = req.getStartTime();
        Date endTime = DateUtil.getEndOfDate(req.getEndTime());

        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);

        List<OfferingRecord> offeringRecords = offeringPurchaseMapper.selectRecordsByUserIdAndPeriod(user.getId(), startTime, endTime, 50);
        List<LanternRecord> lanternRecords = lanternPurchaseMapper.selectRecordsByUserIdAndPeriod(user.getId(), startTime, endTime, 50);

        return Stream.concat(
                offeringRecords
                .stream()
                .map(o -> RecordVO.builder()
                                .date(DateFormatUtils.format(o.getCreateTime(), "yyyy/MM/dd HH:mm"))
                                .item(RecordItem.Offering.getLabel())
                                .content(o.getOfferingName())
                                .build()
                ),
                lanternRecords.stream()
                        .map(l -> RecordVO.builder()
                                .date(DateFormatUtils.format(l.getCreateTime(), "yyyy/MM/dd HH:mm"))
                                .item(RecordItem.Lantern.getLabel())
                                .content(l.getLanternName())
                                .build())
                ).collect(Collectors.toList());
    }

    public void sendResetPasswordMail(String email) {
        try {
            UserExample example = new UserExample();
            example.createCriteria().andEmailEqualTo(email);
            List<User> users = userMapper.selectByExample(example);

            if (users.isEmpty()) {
                throw new ApiException("查無此帳號");
            }
            User user = users.get(0);

            String token = RandomGenerator.getUUID().toLowerCase(Locale.ROOT);

            tokenMapper.deleteSsoTokensByUsername(user.getUsername());

            Token ssoToken = new Token();
            ssoToken.setToken(token);
            ssoToken.setUsername(user.getUsername());
            ssoToken.setExpirationTime(
                    Date.from(LocalDateTime.now().plusMinutes(expiredMinute).atZone(ZoneId.systemDefault()).toInstant())
            );
            ssoToken.setCreateTime(DateUtil.getCurrentDate());
            ssoToken.setType(TokenType.SSO.getValue());
            tokenMapper.insert(ssoToken);

            String resetLink = frontUrl + token;

            Map<String, String> params = new HashMap<>();
            params.put("resetLink", resetLink);
            params.put("expireMinutes", String.valueOf(expiredMinute));
            emailService.sendTemplateMail(user.getEmail(), "密碼重設通知", "forget-password", params);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }
}
