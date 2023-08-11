package blog.in.action;

import blog.in.action.domain.ItemEntity;
import blog.in.action.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class GroupByObjectArrayTest {

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

    void print(List<Object[]> result) {
        for (Object[] array : result) {
            System.out.println(Arrays.stream(array).toList());
        }
    }

    @Test
    void test() {

        var result = sut.findEachCountGroupByItemNameWithObjectArray();


        assertEquals(4, result.size());

        var firstGroupBy = result.get(0);
        assertEquals(5L, firstGroupBy[0]);
        assertEquals(0L, firstGroupBy[1]);
        assertEquals(0L, firstGroupBy[2]);
        assertEquals(0L, firstGroupBy[3]);

        var secondGroupBy = result.get(1);
        assertEquals(0L, secondGroupBy[0]);
        assertEquals(5L, secondGroupBy[1]);
        assertEquals(0L, secondGroupBy[2]);
        assertEquals(0L, secondGroupBy[3]);

        var thirdGroupBy = result.get(2);
        assertEquals(0L, thirdGroupBy[0]);
        assertEquals(0L, thirdGroupBy[1]);
        assertEquals(5L, thirdGroupBy[2]);
        assertEquals(0L, thirdGroupBy[3]);

        var fourthGroupBy = result.get(3);
        assertEquals(0L, fourthGroupBy[0]);
        assertEquals(0L, fourthGroupBy[1]);
        assertEquals(0L, fourthGroupBy[2]);
        assertEquals(5L, fourthGroupBy[3]);

        print(result);
    }
}
