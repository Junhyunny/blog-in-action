package blog.`in`.action

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mockStatic
import java.time.LocalDateTime

class ObjectTimeProviderTest {

    @Test
    fun makeStub() {

        val localDateTime = LocalDateTime.of(2024, 2, 1, 12, 0)
        val mockedLocalDateTime = mockStatic(ObjectTimeProvider::class.java)
        mockedLocalDateTime.`when`<LocalDateTime> { ObjectTimeProvider.currentDateTime() }.thenReturn(localDateTime)


        val result = ObjectTimeProvider.currentDateTime()


        assertEquals(localDateTime, result)
        mockedLocalDateTime.close()
    }
}

object ObjectTimeProvider {

    @JvmStatic
    fun currentDateTime(): LocalDateTime = LocalDateTime.now()
}
