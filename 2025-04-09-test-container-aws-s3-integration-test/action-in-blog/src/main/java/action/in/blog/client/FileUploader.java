package action.in.blog.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Component
public class FileUploader {
    private final String bucket;
    private final S3Client s3Client;

    public FileUploader(
            @Value("${storage.bucket}") String bucket,
            S3Client s3Client
    ) {
        this.bucket = bucket;
        this.s3Client = s3Client;
    }

    public boolean createDirectory(String filePath) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .contentType("application/x-directory")
                            .contentLength(0L)
                            .key(filePath)
                            .build(),
                    RequestBody.fromBytes(new byte[0])
            );
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void createFile(MultipartFile multipartFile, String fileName) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(fileName)
                            .contentType(multipartFile.getContentType())
                            .contentLength(multipartFile.getSize())
                            .build(),
                    RequestBody.fromInputStream(
                            multipartFile.getInputStream(),
                            multipartFile.getSize()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }
}
