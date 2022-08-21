package action.in.blog.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StringPoolTests {

    @Test
    void first_and_second_string_refer_to_same_address() {
        String first = "Junhyuuny";
        String second = "Junhyuuny";

        assertThat(System.identityHashCode(first), equalTo(System.identityHashCode(second)));
        assertThat(first == second, equalTo(true));
        assertThat(first, sameInstance(second));
    }

    @Test
    void literal_string_has_different_address_with_string_object() {
        String literal = "Junhyunny";
        String stringObject = new String("Junhyunny");

        assertThat(System.identityHashCode(literal), not(equalTo(System.identityHashCode(stringObject))));
        assertThat(literal != stringObject, equalTo(true));
        assertThat(literal, not(sameInstance(stringObject)));
    }

    @Test
    void literal_string_has_same_address_with_interned_string() {
        String literal = "Junhyunny";
        String internedString = new String("Junhyunny").intern();

        assertThat(System.identityHashCode(literal), equalTo(System.identityHashCode(internedString)));
        assertThat(literal == internedString, equalTo(true));
        assertThat(literal, sameInstance(internedString));
    }


    @Test
    void guess_results() {
        String first = "Hello";
        String second = "World";
        String third = "HelloWorld";
        String fourth = first + second;

        System.out.println((first + second) == third);
        System.out.println((first + second) == fourth);
        System.out.println((first + second).intern() == third);
        System.out.println(fourth == third);
        System.out.println(fourth == third.intern());
        System.out.println(fourth.intern() == third);
    }
}
