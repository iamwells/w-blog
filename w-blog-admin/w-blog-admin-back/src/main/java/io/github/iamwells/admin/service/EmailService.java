package io.github.iamwells.admin.service;


public interface EmailService {
    void sendSimple(String text);

    void sendCaptcha(String captcha);

    void sendHtml(String html);

}
