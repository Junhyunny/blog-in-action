package action.in.blog;

import action.in.blog.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.junit.jupiter.api.Assertions.*;

public class OperatorsTests {

    @Test
    void operators() {
        ExpressionParser parser = new SpelExpressionParser();


        assertEquals(15, parser.parseExpression("10 + 5").getValue(Integer.class));
        assertEquals(5, parser.parseExpression("10 - 5").getValue(Integer.class));
        assertEquals(5, parser.parseExpression("1 * 5").getValue(Integer.class));
        assertEquals(1, parser.parseExpression("5 % 4").getValue(Integer.class));

        assertTrue(parser.parseExpression("'junhyunny' == 'junhyunny'").getValue(Boolean.class));
        assertTrue(parser.parseExpression("35 > 30").getValue(Boolean.class));
        assertFalse(parser.parseExpression("'xyz' instanceof T(int)").getValue(Boolean.class));
        assertFalse(parser.parseExpression("'5.0067' matches '^-?\\d+(\\.\\d{2})?$'").getValue(Boolean.class));

        assertTrue(parser.parseExpression("true || false").getValue(Boolean.class));
        assertTrue(parser.parseExpression("true or false").getValue(Boolean.class));
        assertFalse(parser.parseExpression("true && false").getValue(Boolean.class));
        assertFalse(parser.parseExpression("true and false").getValue(Boolean.class));
    }

    @Test
    void operatorsWithInstance() {
        Person person = new Person("junhyunny", 35);
        ExpressionParser parser = new SpelExpressionParser();
        Expression sutName = parser.parseExpression("name == 'junhyunny'");
        Expression sutAge = parser.parseExpression("age > 40");


        boolean resultName = sutName.getValue(person, Boolean.class);
        boolean resultAge = sutAge.getValue(person, Boolean.class);


        assertTrue(resultName);
        assertFalse(resultAge);
    }
}
