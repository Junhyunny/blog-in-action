package action.in.blog;

import action.in.blog.client.FileUploader;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MinIOContainer;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActionInBlogApplicationTests {

    static final String TEST_BUCKET_NAME = "test-bucket";

    static MinIOContainer container = new MinIOContainer("minio/minio:latest")
            .withEnv("MINIO_ACCESS_KEY", "DUMMY_KEY_ID")
            .withEnv("MINIO_SECRET_KEY", "DUMMY_ACCESS_KEY");

    @DynamicPropertySource
    static void s3Properties(DynamicPropertyRegistry registry) {
        registry.add(
                "storage.endpoint", () -> "http://localhost:" + container.getMappedPort(9000)
        );
        registry.add(
                "storage.bucket", () -> TEST_BUCKET_NAME
        );
    }

    static S3Client s3Client;

    @BeforeAll
    static void beforeAll() {
        container.start();
        var credentialsProvider = StaticCredentialsProvider
                .create(
                        AwsBasicCredentials
                                .builder()
                                .accessKeyId("DUMMY_KEY_ID")
                                .secretAccessKey("DUMMY_ACCESS_KEY")
                                .build()
                );
        s3Client = S3Client.builder()
                .region(Region.of("ap-northeast-1"))
                .endpointOverride(URI.create("http://localhost:" + container.getMappedPort(9000)))
                .forcePathStyle(true)
                .credentialsProvider(credentialsProvider)
                .build();
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @Autowired
    FileUploader sut;

    @BeforeEach
    void setUp() {
        s3Client.createBucket(
                CreateBucketRequest.builder()
                        .bucket(TEST_BUCKET_NAME)
                        .build()
        );
    }

    @AfterEach
    void tearDown() {
        var result = s3Client.listObjects(
                ListObjectsRequest.builder()
                        .bucket(TEST_BUCKET_NAME)
                        .build()
        );
        if (!result.contents().isEmpty()) {
            var objects = result.contents()
                    .stream()
                    .map(obj -> ObjectIdentifier.builder()
                            .key(obj.key())
                            .build()
                    )
                    .toList();
            s3Client.deleteObjects(
                    DeleteObjectsRequest.builder()
                            .bucket(TEST_BUCKET_NAME)
                            .delete(
                                    Delete.builder()
                                            .objects(objects)
                                            .build()
                            )
                            .build()
            );
        }
        s3Client.deleteBucket(
                DeleteBucketRequest.builder()
                        .bucket(TEST_BUCKET_NAME)
                        .build()
        );
    }

    @Test
    void createDirectory() {
        var result = sut.createDirectory("attachments");


        assertTrue(result);
        var resultObject = s3Client.listObjects(
                ListObjectsRequest
                        .builder()
                        .bucket(TEST_BUCKET_NAME)
                        .build()
        );
        assertEquals(1, resultObject.contents().size());
        var object = resultObject.contents().get(0);
        assertEquals("attachments", object.key());
    }

    @Test
    void createFile() throws IOException {
        var sampleFile = new MockMultipartFile("sample.txt", "HelloWorld".getBytes());


        sut.createFile(sampleFile, "attachments/directory/1/sample.txt");


        var result = s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(TEST_BUCKET_NAME)
                        .key("attachments/directory/1/sample.txt")
                        .build()
        );
        assertArrayEquals("HelloWorld".getBytes(), result.readAllBytes());
    }
}
