package blog.`in`.action.domain

import org.slf4j.LoggerFactory
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class Todo(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val elapsedMessage: String = ""
) {
    companion object {
        private val logger = LoggerFactory.getLogger(Todo::class.java)

        private fun elapsedMessage(localDateTime: LocalDateTime): String {
            val second = 1000
            val currentTimestamp = System.currentTimeMillis()
            val createdAtTimestamp = Timestamp.valueOf(localDateTime).time
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
            val message = "(${(currentTimestamp - createdAtTimestamp) / 60 / second}분 경과)"
            return "${formatter.format(localDateTime)}$message"
        }

        fun of(entity: TodoEntity): Todo {
            logger.info("Timezone - {}", TimeZone.getDefault())
            logger.info("Data created time - {}", entity.createdAt)
            logger.info("JVM application current time - {}", LocalDateTime.now())
            return Todo(
                entity.id,
                entity.title,
                entity.content,
                elapsedMessage(entity.createdAt)
            )
        }
    }
}
