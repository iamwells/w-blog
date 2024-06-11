package io.github.iamwells.admin.service;


import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OOSService {
    String uploadImg(MultipartFile file);

    List<String> listFiles(String bucketName, String folder);
}
