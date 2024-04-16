package io.github.iamwells.admin.service.impl;

import io.github.iamwells.admin.service.MailService;
import io.github.iamwells.admin.util.MailMessageBuilder;
import io.github.iamwells.commons.web.ResponseCommonEntity;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
@ConditionalOnProperty(prefix = "spring.mail", name = "host")
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private TemplateEngine templateEngine;


    @Resource
    private RedissonClient redissonClient;


    @Value("${spring.mail.username}")
    private String from;

    @Resource
    private MailMessageBuilder mailMessageBuilder;


    @Override
    public void sendSimple(List<String> receivers, String subject, String text, File attachment, String attachmentName) {

        try {
            mailMessageBuilder = Optional.ofNullable(mailMessageBuilder).orElseGet(() -> new MailMessageBuilder(mailSender));
            MimeMessage message = mailMessageBuilder.setFromIfEmpty(from)
                    .setReceivers(receivers)
                    .setSubject(subject)
                    .setText(text)
                    .setHtml(true)
                    .setAttachment(attachment)
                    .setAttachmentName(attachmentName)
                    .build();

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseCommonEntity<Object> sendCaptcha(String to, String captcha) {
        Context context = new Context();
        context.setVariable("top", "您的验证码为：");
        context.setVariable("center", captcha);
        context.setVariable("bottom", "仅用于登录验证");
        String mailTemplate = templateEngine.process("mailTemplate", context);
        try {

            mailMessageBuilder = Optional.ofNullable(mailMessageBuilder).orElseGet(() -> new MailMessageBuilder(mailSender));
            MimeMessage message = mailMessageBuilder.setFromIfEmpty(from)
                    .addTo(to)
                    .setSubject("验证码 —— W-Blog")
                    .setText(mailTemplate)
                    .setHtml(true)
                    .build();

            RMap<Object, Object> map = redissonClient.getMap("captcha:" + to + ":");
            String uuid = UUID.randomUUID().toString().replace("-", "");
            map.expire(Duration.ofMinutes(5));
            map.put(uuid, captcha);
            mailSender.send(message);
            return ResponseCommonEntity.OK(Map.of("captchaId", uuid));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendHtml(List<String> receivers, String subject, String html, File attachment, String attachmentName) {

    }


}
