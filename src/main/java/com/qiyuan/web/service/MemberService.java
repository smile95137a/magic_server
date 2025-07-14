package com.qiyuan.web.service;

import com.qiyuan.security.dao.TokenMapper;
import com.qiyuan.security.entity.Token;
import com.qiyuan.security.exception.ApiException;
import com.qiyuan.web.dao.*;
import com.qiyuan.web.dto.request.RecordPeriodRequest;
import com.qiyuan.web.dto.request.ResetPasswordRequest;
import com.qiyuan.web.dto.response.MyGodInfoVO;
import com.qiyuan.web.dto.response.OfferingStateVO;
import com.qiyuan.web.dto.response.RecordVO;
import com.qiyuan.web.entity.*;
import com.qiyuan.web.entity.example.GodExample;
import com.qiyuan.web.entity.example.GodInfoExample;
import com.qiyuan.web.entity.example.OfferingExample;
import com.qiyuan.web.entity.example.UserExample;
import com.qiyuan.web.enums.RecordItem;
import com.qiyuan.web.enums.TokenType;
import com.qiyuan.web.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MemberService {

    private final OfferingPurchaseMapper offeringPurchaseMapper;
    private final LanternPurchaseMapper lanternPurchaseMapper;
    private final GodInfoMapper godInfoMapper;
    private final GodMapper godMapper;
    private final UserMapper userMapper;
    private final TokenMapper tokenMapper;
    private final EmailService emailService;
    private final OfferingMapper offeringMapper;

    @Value("${sso.front-url}")
    private String frontUrl;

    @Value("${sso.expired-minute}")
    private Integer expiredMinute;


    public MemberService(OfferingPurchaseMapper offeringPurchaseMapper, LanternPurchaseMapper lanternPurchaseMapper, GodInfoMapper godInfoMapper, GodMapper godMapper, UserMapper userMapper, TokenMapper tokenMapper, EmailService emailService, OfferingMapper offeringMapper) {
        this.offeringPurchaseMapper = offeringPurchaseMapper;
        this.lanternPurchaseMapper = lanternPurchaseMapper;
        this.godInfoMapper = godInfoMapper;
        this.godMapper = godMapper;
        this.userMapper = userMapper;
        this.tokenMapper = tokenMapper;
        this.emailService = emailService;
        this.offeringMapper = offeringMapper;
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

    @Transactional
    public void verifySsoToken(String token) {
        Token resetToken = tokenMapper.findByToken(token);
        if (resetToken == null) {
            throw new ApiException("連結已失效或不存在");
        }

        if (resetToken.getExpirationTime().before(new Date()))
            throw new ApiException("連結已過期");

        if (Boolean.TRUE.equals(resetToken.getRevoked()))
            throw new ApiException("連結已失效");
    }

    @Transactional
    public boolean resetPassword(ResetPasswordRequest req) {
        Token token = tokenMapper.findByToken(req.getToken());

        if (token == null || token.getExpirationTime().before(new Date())) {
            throw new ApiException("連結已失效或過期");
        }

        if (!ValidationUtil.isValidPassword(req.getNewPassword())) {
            throw new ApiException("密碼請輸入至少一位數字及小寫英文字母");
        }

        User user = userMapper.selectByUsername(token.getUsername());
        if (user == null) throw new ApiException("帳號不存在");

        user.setPassword(req.getNewPassword());
        userMapper.updateByPrimaryKeySelective(user);

        tokenMapper.revokeToken(token.getToken());

        return true;
    }

    public List<MyGodInfoVO> getMyGodInfo() {
        String username = SecurityUtils.getCurrentUsername();
        User user = userMapper.selectByUsername(username);

        GodInfoExample example = new GodInfoExample();
        example.createCriteria()
                .andUserIdEqualTo(user.getId());

        List<GodInfo> godInfoList = godInfoMapper.selectByExample(example);

        if (godInfoList.isEmpty()) return Collections.EMPTY_LIST;

        List<String> godIds = godInfoList.stream().map(GodInfo::getGodId).collect(Collectors.toList());
        GodExample e = new GodExample();
        e.createCriteria().andIdIn(godIds);
        List<God> gods = godMapper.selectByExample(e);
        Map<String, String> godMap = gods.stream()
                .collect(Collectors.toMap(God::getId, God::getName));


        return godInfoList.stream().map(g -> {
            Date start = g.getOnshelfTime();
            Date end = g.getOffshelfTime();
            int totalDays = 0, passDays = 0, remainingDays = 0;
            if (start != null && end != null) {
                LocalDate startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate today = LocalDate.now();

                totalDays = (int) (endDate.toEpochDay() - startDate.toEpochDay());
                passDays = (int) (today.toEpochDay() - startDate.toEpochDay());
                remainingDays = (int) (endDate.toEpochDay() - today.toEpochDay());

                if (passDays < 0) passDays = 0;
                if (remainingDays < 0) remainingDays = 0;
            }

            List<OfferingStateVO> offeringStates = g.getOfferingList() == null || g.getOfferingList().isEmpty() ? Collections.EMPTY_LIST : JsonUtil.fromJsonList(g.getOfferingList(), OfferingStateVO.class);
            List<String> offeringIds = offeringStates.stream().map(OfferingStateVO::getId).collect(Collectors.toList());
            OfferingExample oe = new OfferingExample();
            oe.createCriteria().andIdIn(offeringIds);
            List<Offering> offeringList = offeringMapper.selectByExample(oe);
            Map<String, String> offeringMap = offeringList.stream()
                    .collect(Collectors.toMap(Offering::getId, Offering::getName));

            offeringStates.stream().forEach(o -> {
                o.setId(offeringMap.get(o.getId()));
            });

            return MyGodInfoVO.builder()
                    .godName(godMap.get(g.getGodId()))
                    .totalDays(totalDays)
                    .passDays(passDays)
                    .remainingDays(remainingDays)
                    .offeringStates(offeringStates)
                    .build();
        }).collect(Collectors.toList());
    }


}
