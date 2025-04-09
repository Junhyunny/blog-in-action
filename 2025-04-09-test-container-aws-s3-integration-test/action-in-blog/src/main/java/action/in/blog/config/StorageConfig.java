package action.in.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class StorageConfig {
    @Value("${storage.endpoint}")
    private String endpoint;
    @Value("${storage.region}")
    private String region;
    @Value("${storage.access-key-id}")
    private String accessKeyId;
    @Value("${storage.secret-access-key}")
    private String secretAccessKey;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .endpointOverride(URI.create(endpoint))
                .forcePathStyle(true)
                .credentialsProvider(
                        StaticCredentialsProvider
                                .create(
                                        AwsBasicCredentials
                                                .builder()
                                                .accessKeyId(accessKeyId)
                                                .secretAccessKey(secretAccessKey)
                                                .build()
                                )
                )
                .build();
    }
}
