package com.qiyuan.web.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    /**
     * 發送純文字郵件
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 根據自訂 template 寄出 HTML 郵件（簡易模板替換）
     * @param to 收件人
     * @param subject 標題
     * @param templateName templates目錄下的html檔名（不用加路徑）
     * @param params 變數
     */
    public void sendTemplateMail(String to, String subject, String templateName, Map<String, String> params) throws MessagingException, IOException {
        // 讀取模板
        ClassPathResource resource = new ClassPathResource("email-templates/" + templateName + ".html");
        String template = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // 替換變數
        for (Map.Entry<String, String> entry : params.entrySet()) {
            template = template.replace("${" + entry.getKey() + "}", entry.getValue());
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(template, true);

        mailSender.send(message);
    }
}

