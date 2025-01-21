package action.`in`.blog.repository

import action.`in`.blog.domain.MovieInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromN
import software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromS
import software.amazon.awssdk.services.dynamodb.model.QueryRequest

interface MovieRepository {
    fun findByGenre(genre: String, startYear: Int, endYear: Int): List<MovieInfo>
}

@Repository
class MovieRepositoryImpl(
    @Value("\${amazon.dynamodb.table-name}") val tableName: String,
    private val dynamoDbClient: DynamoDbClient,
) : MovieRepository {

    override fun findByGenre(
        genre: String,
        startYear: Int,
        endYear: Int
    ): List<MovieInfo> {
        return dynamoDbClient.query(
            QueryRequest.builder()
                .tableName(tableName) // 1
                .indexName("GenreYearIndex") // 2
                .keyConditionExpression("#genre = :genre AND #year BETWEEN :startYear AND :endYear") // 3
                .expressionAttributeNames( // 4
                    mapOf(
                        "#genre" to "Genre",
                        "#year" to "Year"
                    )
                )
                .expressionAttributeValues( // 5
                    mapOf(
                        ":genre" to fromS(genre),
                        ":startYear" to fromN(startYear.toString()),
                        ":endYear" to fromN(endYear.toString())
                    )
                )
                .build()
        )
            .items()
            .map { MovieInfo.of(it) }
    }
}

