package blog.in.action;

import blog.in.action.domain.ItemEntity;
import blog.in.action.domain.ItemNameCountVO;
import blog.in.action.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class GroupByCustomClassTest {

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

    void print(List<ItemNameCountVO> result) {
        for (ItemNameCountVO vo : result) {
            System.out.println(vo);
        }
    }

    @Test
    void test() {

        var result = sut.findEachCountGroupByItemNameWithCustomClass();


        assertEquals(4, result.size());

        var firstItem = result.get(0);
        assertEquals(5L, firstItem.getACount());
        assertEquals(0L, firstItem.getBCount());
        assertEquals(0L, firstItem.getCCount());
        assertEquals(0L, firstItem.getDCount());

        var secondItem = result.get(1);
        assertEquals(0L, secondItem.getACount());
        assertEquals(5L, secondItem.getBCount());
        assertEquals(0L, secondItem.getCCount());
        assertEquals(0L, secondItem.getDCount());

        var thirdItem = result.get(2);
        assertEquals(0L, thirdItem.getACount());
        assertEquals(0L, thirdItem.getBCount());
        assertEquals(5L, thirdItem.getCCount());
        assertEquals(0L, thirdItem.getDCount());

        var fourthItem = result.get(3);
        assertEquals(0L, fourthItem.getACount());
        assertEquals(0L, fourthItem.getBCount());
        assertEquals(0L, fourthItem.getCCount());
        assertEquals(5L, fourthItem.getDCount());

        print(result);
    }
}
