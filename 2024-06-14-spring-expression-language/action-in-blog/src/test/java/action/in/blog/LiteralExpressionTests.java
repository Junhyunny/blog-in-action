package action.in.blog;

import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LiteralExpressionTests {

    @Test
    void literal() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("'Hello World'");


        String result = sut.getValue(String.class);


        assertEquals("Hello World", result);
    }

    @Test
    void useMethod() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("'Hello World'.concat('!')");


        String result = sut.getValue(String.class);


        assertEquals("Hello World!", result);
    }

    @Test
    void accessProperty() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("'Hello World'.bytes");


        byte[] result = sut.getValue(byte[].class);


        assertArrayEquals("Hello World".getBytes(), result);
    }

    @Test
    void accessNestedProperty() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("'Hello World'.bytes.length");


        int result = sut.getValue(Integer.class);


        assertEquals(11, result);
    }

    @Test
    void useConstructor() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("new String('hello world').toUpperCase()");


        String result = sut.getValue(String.class);


        assertEquals("HELLO WORLD", result);
    }
}
