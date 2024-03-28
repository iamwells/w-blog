package io.github.iamwells.admin.service;


import io.github.iamwells.commons.web.ResponseCommonEntity;

import java.io.File;
import java.util.List;

public interface MailService {


    void sendSimple(List<String> receivers, String subject, String text, File attachment, String attachmentName);

    ResponseCommonEntity<Object> sendCaptcha(String to, String captcha);

    void sendHtml(List<String> receivers, String subject, String html, File attachment, String attachmentName);

}
