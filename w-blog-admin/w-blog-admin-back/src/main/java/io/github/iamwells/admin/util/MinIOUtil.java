package io.github.iamwells.admin.util;

import com.google.common.collect.Lists;
import io.github.iamwells.admin.properties.MinIOProperties;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class MinIOUtil {

    private final MinioClient minioClient;

    private final MinIOProperties minIOProperties;

    public MinIOUtil(MinioClient minioClient, MinIOProperties minIOProperties) {
        this.minioClient = minioClient;
        this.minIOProperties = minIOProperties;
    }

    public static String getUUIDFileName(MultipartFile file, String folder) {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = null;
        if (originalFilename != null) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        folder = (folder == null || folder.isEmpty()) ? folder : folder + "/";
        // 使用UUID生成唯一文件名
        return folder + UUID.randomUUID() + fileExtension;
    }

    public void createBucket(String bucketName) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (InsufficientDataException | InternalException | InvalidKeyException | IOException |
                 NoSuchAlgorithmException | XmlParserException | ErrorResponseException | InvalidResponseException |
                 ServerException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadObject(String bucketName, MultipartFile file, String fileName) {
        createBucket(bucketName);
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName).stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeObject(String bucketName, String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Result<Item>> listObjects(String bucketName, int maxKeys, String prefix) {
        ListObjectsArgs.Builder builder = ListObjectsArgs.builder().bucket(bucketName).maxKeys(maxKeys);
        if (prefix != null && !prefix.isEmpty()) {
            builder.prefix(prefix);
        }
        ListObjectsArgs listObjectsArgs = builder.build();
        Iterable<Result<Item>> results = minioClient.listObjects(listObjectsArgs);
        return Lists.newArrayList(results);
    }

    public String getObjectShareUrl(String bucketName, String fileName, int days) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .expiry(days, TimeUnit.DAYS)
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException |
                 ServerException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl(String bucketName, String fileName) {
        String endpoint = minIOProperties.getEndpoint();
        endpoint = endpoint.endsWith("/") ? endpoint : endpoint + "/";
        return endpoint + bucketName + "/" + fileName;
    }

}
