package management.project.movie.service;

import io.minio.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.InputStream;

@Service
public class MinIOService {

    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String defaultBucketName;

    @Autowired
    public MinIOService(@Value("${minio.endpoint}") String endpoint,
                        @Value("${minio.access-key}") String accessKey,
                        @Value("${minio.secret-key}") String secretKey) {
        this.minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    @SneakyThrows
    public void uploadFile(String bucketName, String objectName, String filePath) {
        if (bucketName == null) {
            bucketName = defaultBucketName;
        }
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        minioClient.uploadObject(UploadObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .filename(filePath)
                .build());

        System.out.println("File uploaded successfully in MinIO!");
    }

    @SneakyThrows
    public void downloadFileToLocal(String bucketName, String objectName, String localFilePath) {
        try (InputStream inputStream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
             FileOutputStream outputStream = new FileOutputStream(localFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}

