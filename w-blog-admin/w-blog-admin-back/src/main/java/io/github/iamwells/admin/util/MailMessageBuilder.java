package io.github.iamwells.admin.util;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Component
public class MailMessageBuilder {

    @Resource
    private JavaMailSender mailSender;

    private String from;

    private List<String> receivers;

    private String replyTo;

    private String subject;

    private String text;

    private Boolean html;

    private File attachment;

    private String attachmentName;


    public MailMessageBuilder(JavaMailSender mailSender) {
        this.mailSender = Optional.ofNullable(mailSender).orElse(new JavaMailSenderImpl());
    }

    private MailMessageBuilder() {
    }

    public MailMessageBuilder setFromIfEmpty(String from) {
        this.from = Optional.of(this.from).orElse(from);
        return this;
    }

    public MailMessageBuilder setReceivers(List<String> receivers) {
        this.receivers = receivers;
        return this;
    }

    public MailMessageBuilder setReplyTo(String replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public MailMessageBuilder setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public MailMessageBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public MailMessageBuilder setHtml(Boolean html) {
        this.html = html;
        return this;
    }

    public MailMessageBuilder setAttachment(File attachment) {
        this.attachment = attachment;
        return this;
    }

    public MailMessageBuilder setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
        return this;
    }

    public MimeMessage build() throws MessagingException {
        assert mailSender != null : "JavaMailSender未找到，请注册到容器或通过Setter注入！";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        assert from != null && !from.isEmpty() : "发送者(from)邮箱地址未找到，请通过配置或Setter注入！";

        helper.setFrom(from);

        assert receivers != null && !receivers.isEmpty() : "接收者送者(receivers)未找到，请通过Setter注入！";
        receivers.forEach(to -> {
            try {
                helper.addTo(to);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });

        helper.setSubject(subject);

        helper.setText(text, html);

        Optional.ofNullable(attachment).ifPresent(file -> {
            try {
                String finalAttachmentName = Optional.ofNullable(attachmentName).orElse(attachment.getName());
                helper.addAttachment(finalAttachmentName, file);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
        Optional.ofNullable(replyTo).ifPresentOrElse(replyToStr -> {
            try {
                helper.setReplyTo(replyToStr);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }, () -> {
            if (replyTo.isEmpty()) {
                try {
                    helper.setReplyTo(from);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return message;
    }

}
