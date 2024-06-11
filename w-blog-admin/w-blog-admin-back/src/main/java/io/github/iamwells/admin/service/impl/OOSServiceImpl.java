package io.github.iamwells.admin.service.impl;


import io.github.iamwells.admin.service.OOSService;
import io.github.iamwells.admin.util.MinIOUtil;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OOSServiceImpl implements OOSService {

    private final MinIOUtil minIOUtil;

    public OOSServiceImpl(MinIOUtil minIOUtil) {
        this.minIOUtil = minIOUtil;
    }

    @Override
    public String uploadImg(MultipartFile file) {
        String contentType = file.getContentType();
        if (StringUtils.hasLength(contentType) && !contentType.contains("image")) {
            throw new RuntimeException("上传格式错误，应为image类文件！");
        }
        String uuidFileName = MinIOUtil.getUUIDFileName(file, "");
        minIOUtil.uploadObject("images", file, uuidFileName);
        return minIOUtil.getUrl("images", uuidFileName);
    }

    @Override
    public List<String> listFiles(String bucketName, String prefix) {
        List<Result<Item>> results = minIOUtil.listObjects(bucketName, 100, prefix);
        return results.parallelStream().map(result -> {
            try {
                return minIOUtil.getUrl(bucketName,result.get().objectName());
            } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                     InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                     XmlParserException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}
