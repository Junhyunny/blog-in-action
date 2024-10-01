package action.`in`.blog.repository.entity

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey

@DynamoDbBean
data class TodoEntity(
    @get:DynamoDbPartitionKey @get:DynamoDbAttribute(value = "PK") var pk: String,
    @get:DynamoDbSortKey @get:DynamoDbAttribute(value = "SK") var sk: String,
    @get:DynamoDbAttribute(value = "id") var id: String,
    @get:DynamoDbAttribute(value = "title") var title: String,
    @get:DynamoDbAttribute(value = "content") var content: String,
) {
    constructor() : this("", "", "", "", "")
}
