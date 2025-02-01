package action.`in`.blog

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

open class User(val name: String) {
    constructor() : this("")
}

class KotlinObjectMapperSerializeTest {

    @Test
    fun serializeWithClass() {
        val sut = ObjectMapper()
        sut.activateDefaultTyping(
            BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Any::class.java)
                .build(),
            ObjectMapper.DefaultTyping.NON_FINAL
        )


        val result = sut.writeValueAsString(
            User("junhyunny")
        )


        Assertions.assertEquals(
            """
                ["action.in.blog.User",{"name":"junhyunny"}]
            """.trimIndent(),
            result
        )
    }

    @Test
    fun recordSerializeWithClass() {
        data class DataClassUser(val name: String)
        val sut = ObjectMapper()
        sut.activateDefaultTyping(
            BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Any::class.java)
                .build(),
            ObjectMapper.DefaultTyping.EVERYTHING
        )


        val result = sut.writeValueAsString(
            DataClassUser("junhyunny")
        )


        Assertions.assertEquals(
            """
                ["action.in.blog.KotlinObjectMapperSerializeTest${'$'}recordSerializeWithClass${'$'}RecordUser",{"name":"junhyunny"}]
                """.trimIndent(),
            result
        )
    }
}