package action.`in`.blog.repository

import action.`in`.blog.repository.entity.TodoEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.Key
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.IgnoreNullsMode
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest
import software.amazon.awssdk.enhanced.dynamodb.model.UpdateItemEnhancedRequest

@Repository
class TodoRepository(
    @Value("\${amazon.dynamodb.table-name}") val tableName: String,
    dynamoDbEnhancedClient: DynamoDbEnhancedClient,
) {
    val todoEntityTable: DynamoDbTable<TodoEntity> by lazy {
        dynamoDbEnhancedClient.table(
            tableName,
            TableSchema.fromBean(TodoEntity::class.java),
        )
    }

    fun getTodos(): List<TodoEntity> {
        val queryRequest =
            QueryEnhancedRequest
                .builder()
                .queryConditional(
                    QueryConditional.sortBeginsWith(
                        Key
                            .builder()
                            .partitionValue("TODO")
                            .sortValue("ID")
                            .build(),
                    ),
                ).build()
        return todoEntityTable
            .query(queryRequest)
            .items()
            .toList()
    }

    fun getTodo(id: String): TodoEntity =
        todoEntityTable.getItem(
            Key
                .builder()
                .partitionValue("TODO")
                .sortValue("ID#$id")
                .build(),
        )

    fun createTodo(todoEntity: TodoEntity) {
        todoEntityTable.putItem(todoEntity)
    }

    fun updateTodo(todoEntity: TodoEntity) {
        val updateRequest =
            UpdateItemEnhancedRequest
                .builder(TodoEntity::class.java)
                .item(todoEntity)
                .ignoreNullsMode(
                    IgnoreNullsMode.SCALAR_ONLY,
                ).build()
        todoEntityTable.updateItem(updateRequest)
    }

    fun deleteTodo(
        id: String,
    ) {
        todoEntityTable.deleteItem(
            Key
                .builder()
                .partitionValue("TODO")
                .sortValue("ID#$id")
                .build(),
        )
    }
}
