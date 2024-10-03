package action.`in`.blog

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
import software.amazon.awssdk.services.dynamodb.model.ScanRequest
import java.net.URI

abstract class MockRepositoryConfig {
    protected companion object {
        const val TEST_TABLE_NAME = "TestActionInBlog"
        var dynamoDbClient: DynamoDbClient

        private val dynamoDbContainer =
            GenericContainer<Nothing>(
                DockerImageName
                    .parse(
                        "public.ecr.aws/aws-dynamodb-local/aws-dynamodb-local:latest",
                    ).asCompatibleSubstituteFor("amazon/dynamodb-local"),
            ).apply {
                withExposedPorts(8000)
            }

        init {
            dynamoDbContainer.start()
            dynamoDbClient =
                DynamoDbClient
                    .builder()
                    .region(Region.AP_NORTHEAST_1)
                    .endpointOverride(URI.create("http://localhost:${dynamoDbContainer.getMappedPort(8000)}"))
                    .credentialsProvider(
                        StaticCredentialsProvider.create(
                            AwsBasicCredentials.create("dummy", "dummy"),
                        ),
                    ).build()
            createTable()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerDynamoDbProperties(registry: DynamicPropertyRegistry) {
            registry.add("amazon.dynamodb.endpoint") { "http://localhost:${dynamoDbContainer.getMappedPort(8000)}" }
            registry.add("amazon.dynamodb.table-name") { TEST_TABLE_NAME }
        }

        private fun deleteTableIfExists() {
            val tables = dynamoDbClient.listTables()
            if (tables.tableNames().contains(TEST_TABLE_NAME)) {
                dynamoDbClient.deleteTable(
                    DeleteTableRequest
                        .builder()
                        .tableName(TEST_TABLE_NAME)
                        .build()
                )
            }
        }

        private fun createTable() {
            val createTableRequest =
                CreateTableRequest
                    .builder()
                    .attributeDefinitions(
                        AttributeDefinition
                            .builder()
                            .attributeName("PK")
                            .attributeType(ScalarAttributeType.S)
                            .build(),
                        AttributeDefinition
                            .builder()
                            .attributeName("SK")
                            .attributeType(ScalarAttributeType.S)
                            .build(),
                    ).keySchema(
                        KeySchemaElement
                            .builder()
                            .attributeName("PK")
                            .keyType(KeyType.HASH)
                            .build(),
                        KeySchemaElement
                            .builder()
                            .attributeName("SK")
                            .keyType(KeyType.RANGE)
                            .build(),
                    ).provisionedThroughput(
                        ProvisionedThroughput
                            .builder()
                            .readCapacityUnits(1)
                            .writeCapacityUnits(1)
                            .build(),
                    )
                    .tableName(TEST_TABLE_NAME)
                    .build()
            dynamoDbClient.createTable(createTableRequest)

            val describeTableRequest =
                DescribeTableRequest
                    .builder()
                    .tableName(TEST_TABLE_NAME)
                    .build()
            dynamoDbClient.waiter().waitUntilTableExists(describeTableRequest)
        }
    }

    fun clearDatabase() {
        dynamoDbClient
            .scan(
                ScanRequest
                    .builder()
                    .tableName(TEST_TABLE_NAME)
                    .build(),
            )
            .items()
            .forEach {
                dynamoDbClient.deleteItem(
                    DeleteItemRequest
                        .builder()
                        .tableName(TEST_TABLE_NAME)
                        .key(mapOf("PK" to it["PK"], "SK" to it["SK"]))
                        .build(),
                )
            }
    }
}