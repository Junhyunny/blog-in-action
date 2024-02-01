package blog.`in`.action

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.DayOfWeek
import java.time.LocalDateTime

class InstanceTimeProviderTest {

    @Test
    fun makeStub() {

        val localDateTime = LocalDateTime.of(2024, 2, 1, 12, 0)
        val mockTimeProvider = mock(InstanceTimeProvider::class.java)
        val sut = SystemUnderTest(mockTimeProvider)
        `when`(mockTimeProvider.currentDateTime()).thenReturn(localDateTime)


        val result = sut.getDayOfTheWeek()


        Assertions.assertEquals("Thursday", result)
    }
}

class InstanceTimeProvider {

    fun currentDateTime(): LocalDateTime = LocalDateTime.now()
}

class SystemUnderTest(private val timeProvider: InstanceTimeProvider) {
    fun getDayOfTheWeek(): String = when (timeProvider.currentDateTime().dayOfWeek) {
        DayOfWeek.MONDAY -> "Monday"
        DayOfWeek.TUESDAY -> "Tuesday"
        DayOfWeek.WEDNESDAY -> "Wednesday"
        DayOfWeek.THURSDAY -> "Thursday"
        DayOfWeek.FRIDAY -> "Friday"
        DayOfWeek.SATURDAY -> "Saturday"
        DayOfWeek.SUNDAY -> "Sunday"
    }
}