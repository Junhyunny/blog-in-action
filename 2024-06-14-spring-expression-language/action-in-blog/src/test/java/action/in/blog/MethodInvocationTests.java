package action.in.blog;

import action.in.blog.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodInvocationTests {

    @Test
    void methodInvocation() {
        Person person = new Person("junhyunny", 35);
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("info()");


        String result = sut.getValue(person, String.class);


        assertEquals("[name: junhyunny, age: 35]", result);
    }
}
