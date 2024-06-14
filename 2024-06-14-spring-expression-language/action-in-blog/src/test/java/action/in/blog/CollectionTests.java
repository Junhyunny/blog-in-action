package action.in.blog;

import action.in.blog.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollectionTests {

    @Test
    void firstItem() {
        List<Person> people = Arrays.asList(new Person("John", 25), new Person("Jane", 30));
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("#root[0]");


        Person result = sut.getValue(people, Person.class);


        assertEquals("John", result.name());
        assertEquals(25, result.age());
    }

    @Test
    void filter() {
        List<Person> people = Arrays.asList(new Person("John", 25), new Person("Jane", 30), new Person("Junhyunny", 35));
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("#root.?[age > 25]");


        List<Person> result = sut.getValue(people, List.class);


        assertEquals("Jane", result.get(0).name());
        assertEquals(30, result.get(0).age());
        assertEquals("Junhyunny", result.get(1).name());
        assertEquals(35, result.get(1).age());
    }
}
