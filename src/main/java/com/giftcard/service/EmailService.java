package com.giftcard.service;

import com.giftcard.model.dto.CardResponseDTO;
import com.giftcard.model.dto.UserInfoDTO;
import com.giftcard.util.DateUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.giftcard.constant.ApplicationConstants.GIFT_CARD_EMAIL_SUBJECT;
import static com.giftcard.constant.ApplicationConstants.GIFT_CARD_EMAIL_TEMPLATE;
import static com.giftcard.constant.ApplicationConstants.VALIDATE_CARD_EMAIL_SUBJECT;
import static com.giftcard.constant.ApplicationConstants.VALIDATE_CARD_EMAIL_TEMPLATE;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.username}")
    private String senderEmail;

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Async
    public void sendEmail(String to, String subject, String template, Map<String, Object> variables) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            messageHelper.setFrom(senderEmail);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);

            Context context = new Context();
            context.setVariables(variables);
            String htmlContent = templateEngine.process(template, context);
            messageHelper.setText(htmlContent, true);

            mailSender.send(message);
            logger.info("Email sent to {} successfully!", to);
        } catch (MessagingException e) {
            logger.error("Failed to send email to {} -> Error cause: {}", to, e.getMessage());
        }
    }

    public void sendGiftCardEmail(UserInfoDTO userInfo, CardResponseDTO response) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("fullName", userInfo.getFullName());
        variables.put("cardId", response.getCardId());
        variables.put("value", response.getValue());
        variables.put("validUntil", DateUtils.getFormattedDate(response.getValidUntil()));
        sendEmail(userInfo.getEmail(), GIFT_CARD_EMAIL_SUBJECT, GIFT_CARD_EMAIL_TEMPLATE, variables);
    }

    public void sendCardValidationEmail(UserInfoDTO userInfo, CardResponseDTO response) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("fullName", userInfo.getFullName());
        variables.put("cardId", response.getCardId());
        variables.put("validUntil", DateUtils.getFormattedDate(response.getValidUntil()));
        sendEmail(userInfo.getEmail(), VALIDATE_CARD_EMAIL_SUBJECT, VALIDATE_CARD_EMAIL_TEMPLATE, variables);
    }
}
