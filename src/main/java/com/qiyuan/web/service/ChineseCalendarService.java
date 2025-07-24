package com.qiyuan.web.service;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.qiyuan.web.util.RsaKeyUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChineseCalendarService {

    private final RsaKeyUtil rsaKeyUtil;
    
    private final RestTemplate restTemplate;

    @Value("${calendar-api.url}")
    private String apiUrl;

    @Value("${calendar-api.token}")
    private String ecToken;
    
    


    public String queryCalendar(int year, int month, int day) throws Exception {
        String ut = String.valueOf(System.currentTimeMillis());

        Map<String, Integer> queryParams = new TreeMap<>();
        queryParams.put("year", year);
        queryParams.put("month", month);
        queryParams.put("day", day);

        String queryString = queryParams.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        String qr = "?" + queryString + "&ut=" + ut;
        String contentToSign = qr + ut;

        PrivateKey privateKey = rsaKeyUtil.loadRSAPrivateKey("cmp2_ec_private.pem");
        String signature = signSha256WithRSA(contentToSign, privateKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-FT-UT", ut);
        headers.set("X-FT-TOKEN", String.format("EC %s", ecToken));
        headers.set("X-FT-SIGNATURE", signature);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String fullUrl = String.format("%s?year=%d&month=%d&day=%d", apiUrl, year, month, day);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    private static String signSha256WithRSA(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        String base64Encoded = Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
        signature.update(base64Encoded.getBytes(StandardCharsets.UTF_8));
        byte[] signed = signature.sign();
        return Base64.getEncoder().encodeToString(signed);
    }

}
