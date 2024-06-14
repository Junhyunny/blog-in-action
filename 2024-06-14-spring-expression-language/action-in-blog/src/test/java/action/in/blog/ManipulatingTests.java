package action.in.blog;

import action.in.blog.model.Car;
import action.in.blog.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManipulatingTests {

    @Test
    void singleInstance() {
        Car car = new Car("Hyundai");
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("brand");


        sut.setValue(car, "BMW");


        assertEquals("BMW", car.getBrand());
    }

    @Test
    void collectionInstance() {
        List<Person> people = new ArrayList<>(Collections.singleton(new Person("John", 25)));
        ExpressionParser parser = new SpelExpressionParser();
        Expression sut = parser.parseExpression("#root.add(new action.in.blog.model.Person('Jane', 30))");


        sut.getValue(people, List.class);


        assertEquals("John", people.get(0).name());
        assertEquals(25, people.get(0).age());
        assertEquals("Jane", people.get(1).name());
        assertEquals(30, people.get(1).age());
    }
}
