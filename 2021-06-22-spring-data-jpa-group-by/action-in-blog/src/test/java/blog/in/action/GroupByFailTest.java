package blog.in.action;

import blog.in.action.domain.ItemEntity;
import blog.in.action.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.convert.ConverterNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class GroupByFailTest {

    @Autowired
    ItemRepository sut;

    @BeforeEach
    void setup() {
        for (int index = 0; index < 20; index++) {
            sut.save(
                    ItemEntity.builder()
                            .name(Character.toString('A' + (index % 4)))
                            .build()
            );
        }
    }

    @Test
    void test() {

        Throwable result = assertThrows(ConverterNotFoundException.class, () -> {
            sut.findEachCountGroupByItemName();
        });


        assertTrue(
                result.getMessage()
                        .contains("No converter found capable of converting from type [org.springframework.data.jpa.repository.query.")
        );
    }
}
