package action.in.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class User {

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}

public class JavaObjectMapperSerializeTest {

    @Test
    void serialize() throws JsonProcessingException {
        var sut = new ObjectMapper();


        var result = sut.writeValueAsString(
                new User("junhyunny")
        );


        assertEquals(
                """
                        {"name":"junhyunny"}""",
                result
        );
    }

    @Test
    void serializeWithClass() throws JsonProcessingException {
        var sut = new ObjectMapper();
        sut.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                        .allowIfBaseType(Object.class)
                        .build(),
                ObjectMapper.DefaultTyping.NON_FINAL
        );


        var result = sut.writeValueAsString(
                new User("junhyunny")
        );


        assertEquals(
                """
                        ["action.in.blog.User",{"name":"junhyunny"}]""",
                result
        );
    }

    @Test
    void recordSerializeWithClass() throws JsonProcessingException {
        record RecordUser(String name) {
        }
        var sut = new ObjectMapper();
        sut.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                        .allowIfBaseType(Object.class)
                        .build(),
                ObjectMapper.DefaultTyping.EVERYTHING
        );


        var result = sut.writeValueAsString(
                new RecordUser("junhyunny")
        );


        assertEquals(
                """
                        ["action.in.blog.JavaObjectMapperSerializeTest$1RecordUser",{"name":"junhyunny"}]""",
                result
        );
    }
}
