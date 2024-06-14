package action.in.blog;

import action.in.blog.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EvaluationContextTests {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void rootObject() {
        Person person = new Person("junhyunny", 35);
        StandardEvaluationContext context = new StandardEvaluationContext(person);
        ExpressionParser parser = new SpelExpressionParser();
        Expression sutName = parser.parseExpression("name");
        Expression sutAge = parser.parseExpression("age");


        String resultName = sutName.getValue(context, String.class);
        int resultAge = sutAge.getValue(context, Integer.class);


        assertEquals("junhyunny", resultName);
        assertEquals(35, resultAge);
    }

    @Test
    void variable() {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("greeting", "Hello World");
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("#greeting");


        String result = sut.getValue(context, String.class);


        assertEquals("Hello World", result);
    }

    static String reverse(String in) {
        return new StringBuffer(in).reverse().toString();
    }

    @Test
    void function() throws NoSuchMethodException {
        Method reverseMethod = EvaluationContextTests.class.getDeclaredMethod("reverse", String.class);
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.registerFunction("reverse", reverseMethod);
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("#reverse('Hello World')");


        String result = sut.getValue(context, String.class);


        assertEquals("dlroW olleH", result);
    }

    @Test
    void springBean() {
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("@fooService.get()");


        String result = sut.getValue(context, String.class);


        assertEquals("Hello Foo Service", result);
    }
}

