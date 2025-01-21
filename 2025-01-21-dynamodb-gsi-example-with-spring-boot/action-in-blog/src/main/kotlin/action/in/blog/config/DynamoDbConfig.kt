package action.`in`.blog.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import java.net.URI

@Configuration
class DynamoDbConfig(
    @Value("\${amazon.dynamodb.endpoint}") private val endpoint: String, // 1
) {
    @Bean
    fun dynamoDbClient(): DynamoDbClient {
        val builder = DynamoDbClient.builder().region(Region.AP_NORTHEAST_1)
        if (endpoint != "default") { // 2
            builder
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(
                    StaticCredentialsProvider.create(
                        AwsBasicCredentials.create("dummy", "dummy"),
                    ),
                )
        }
        return builder.build()
    }
}
