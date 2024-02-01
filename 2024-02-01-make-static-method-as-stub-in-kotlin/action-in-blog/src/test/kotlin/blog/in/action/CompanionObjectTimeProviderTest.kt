package blog.`in`.action

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mockStatic
import java.time.LocalDateTime

class CompanionObjectTimeProviderTest {

    @Test
    fun makeStub() {

        val localDateTime = LocalDateTime.of(2024, 2, 1, 12, 0)
        val mockedLocalDateTime = mockStatic(CompanionObjectTimeProvider::class.java)
        mockedLocalDateTime.`when`<LocalDateTime> { CompanionObjectTimeProvider.currentDateTime() }.thenReturn(localDateTime)


        val result = CompanionObjectTimeProvider.currentDateTime()


        assertEquals(localDateTime, result)
        mockedLocalDateTime.close()
    }

    @Test
    fun methodCompare () {
        assertEquals(CompanionObjectTimeProvider::currentDateTime, CompanionObjectTimeProvider.Companion::currentDateTime)
    }
}

class CompanionObjectTimeProvider {
    companion object {
        @JvmStatic
        fun currentDateTime(): LocalDateTime = LocalDateTime.now()
    }
}
