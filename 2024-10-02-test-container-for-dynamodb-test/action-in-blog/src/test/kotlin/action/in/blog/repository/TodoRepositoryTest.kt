package action.`in`.blog.repository

import action.`in`.blog.MockRepositoryConfig
import action.`in`.blog.repository.entity.TodoEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromS
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest
class TodoRepositoryTest : MockRepositoryConfig() {

    @Autowired
    lateinit var sut: TodoRepository

    @BeforeEach
    fun setUp() {
        clearDatabase()
    }

    @Test
    fun when_getTodos_then_return_todoEntities() {
        val uuid = UUID.randomUUID()
        dynamoDbClient.putItem(
            PutItemRequest.builder()
                .tableName(TEST_TABLE_NAME)
                .item(
                    mapOf(
                        "PK" to fromS("TODO"),
                        "SK" to fromS("ID#20241003213000-${uuid}"),
                        "id" to fromS(uuid.toString()),
                        "title" to fromS("Hello World"),
                        "content" to fromS("This is the first todo."),
                    )
                )
                .build()
        )


        val result = sut.getTodos()


        assertEquals(1, result.size)
        assertEquals("TODO", result[0].pk)
        assertEquals("ID#20241003213000-${uuid}", result[0].sk)
        assertEquals(uuid.toString(), result[0].id)
        assertEquals("Hello World", result[0].title)
        assertEquals("This is the first todo.", result[0].content)
    }

    @Test
    fun when_getTodo_then_return_todoEntity() {
        val uuid = UUID.randomUUID()
        dynamoDbClient.putItem(
            PutItemRequest.builder()
                .tableName(TEST_TABLE_NAME)
                .item(
                    mapOf(
                        "PK" to fromS("TODO"),
                        "SK" to fromS("ID#20241003213000-${uuid}"),
                        "id" to fromS(uuid.toString()),
                        "title" to fromS("Hello World"),
                        "content" to fromS("This is the first todo."),
                    )
                )
                .build()
        )


        val result = sut.getTodo("20241003213000-${uuid}")


        assertEquals("TODO", result.pk)
        assertEquals("ID#20241003213000-${uuid}", result.sk)
        assertEquals(uuid.toString(), result.id)
        assertEquals("Hello World", result.title)
        assertEquals("This is the first todo.", result.content)
    }

    @Test
    fun when_createTodo_then_find_todoEntity_in_dynamoDb() {
        val uuid = UUID.randomUUID()
        sut.createTodo(
            TodoEntity(
                "TODO",
                "ID#20241003213000-${uuid}",
                uuid.toString(),
                "Hello World",
                "This is the first todo."
            )
        )

        val result = dynamoDbClient.getItem(
            GetItemRequest.builder()
                .tableName(TEST_TABLE_NAME)
                .key(
                    mapOf(
                        "PK" to fromS("TODO"),
                        "SK" to fromS("ID#20241003213000-${uuid}"),
                    )
                )
                .build()
        )
        assertEquals(fromS("TODO"), result.item()["PK"])
        assertEquals(fromS("ID#20241003213000-${uuid}"), result.item()["SK"])
        assertEquals(fromS(uuid.toString()), result.item()["id"])
        assertEquals(fromS("Hello World"), result.item()["title"])
        assertEquals(fromS("This is the first todo."), result.item()["content"])
    }

    @Test
    fun given_todoEntity_is_existed_when_updateTodo_then_get_updated_entity() {
        val uuid = UUID.randomUUID()
        dynamoDbClient.putItem(
            PutItemRequest.builder()
                .tableName(TEST_TABLE_NAME)
                .item(
                    mapOf(
                        "PK" to fromS("TODO"),
                        "SK" to fromS("ID#20241003213000-${uuid}"),
                        "id" to fromS(uuid.toString()),
                        "title" to fromS("Hello World"),
                        "content" to fromS("This is the first todo."),
                    )
                )
                .build()
        )


        sut.updateTodo(
            TodoEntity(
                "TODO",
                "ID#20241003213000-${uuid}",
                null,
                null,
                "This is the second todo."
            )
        )


        val result = dynamoDbClient.getItem(
            GetItemRequest.builder()
                .tableName(TEST_TABLE_NAME)
                .key(
                    mapOf(
                        "PK" to fromS("TODO"),
                        "SK" to fromS("ID#20241003213000-${uuid}"),
                    )
                )
                .build()
        )
        assertEquals(fromS("TODO"), result.item()["PK"])
        assertEquals(fromS("ID#20241003213000-${uuid}"), result.item()["SK"])
        assertEquals(fromS(uuid.toString()), result.item()["id"])
        assertEquals(fromS("Hello World"), result.item()["title"])
        assertEquals(fromS("This is the second todo."), result.item()["content"])
    }

    @Test
    fun given_todoEntity_is_existed_when_deleteTodo_then_not_found_entity_in_dynamoDb() {
        val uuid = UUID.randomUUID()
        dynamoDbClient.putItem(
            PutItemRequest.builder()
                .tableName(TEST_TABLE_NAME)
                .item(
                    mapOf(
                        "PK" to fromS("TODO"),
                        "SK" to fromS("ID#20241003213000-${uuid}"),
                        "id" to fromS(uuid.toString()),
                        "title" to fromS("Hello World"),
                        "content" to fromS("This is the first todo."),
                    )
                )
                .build()
        )


        sut.deleteTodo("20241003213000-${uuid}")


        val result = dynamoDbClient.getItem(
            GetItemRequest.builder()
                .tableName(TEST_TABLE_NAME)
                .key(
                    mapOf(
                        "PK" to fromS("TODO"),
                        "SK" to fromS("ID#20241003213000-${uuid}"),
                    )
                )
                .build()
        )
        assertTrue(result.item().isEmpty())
    }
}