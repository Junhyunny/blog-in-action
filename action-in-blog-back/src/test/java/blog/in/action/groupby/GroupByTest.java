package blog.in.action.groupby;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConverterNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Log4j2
@SpringBootTest
public class GroupByTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void beforeEach() {
        itemRepository.deleteAll();
        for (int index = 0; index < 20; index++) {
            itemRepository.save(new Item(String.valueOf((char) ('A' + new Random().nextInt(5)))));
        }
    }

    @Test
    public void test_throwException_when_usingClass() {
        assertThrows(ConverterNotFoundException.class, () -> itemRepository.findItemNameGroupUsingClass());
    }

    @Test
    public void test_doesNotThrowException_when_usingObjectArray() {
        assertDoesNotThrow(() -> itemRepository.findItemNameGroupUsingObjectArray());
        itemRepository.findItemNameGroupUsingObjectArray().stream().forEach(objects -> {
            StringBuffer buffer = new StringBuffer("[");
            for (Object obj : objects) {
                buffer.append(obj).append(", ");
            }
            log.info(buffer.append("]"));
        });
    }
}

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_ITEM")
class Item {

    public Item(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;
}

@Getter
@Setter
@NoArgsConstructor
@ToString
class ItemNameGroupVo {

    int aCount;
    int bCount;
    int cCount;
    int dCount;
    int eCount;
}

interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT SUM(CASE WHEN NAME = 'A' THEN 1 ELSE 0 END) AS aCount, "
        + " SUM(CASE WHEN NAME = 'B' THEN 1 ELSE 0 END) AS bCount, "
        + " SUM(CASE WHEN NAME = 'C' THEN 1 ELSE 0 END) AS cCount, "
        + " SUM(CASE WHEN NAME = 'D' THEN 1 ELSE 0 END) AS dCount, "
        + " SUM(CASE WHEN NAME = 'E' THEN 1 ELSE 0 END) AS eCount"
        + " FROM TB_ITEM GROUP BY NAME", nativeQuery = true)
    List<ItemNameGroupVo> findItemNameGroupUsingClass();

    @Query(value = "SELECT SUM(CASE WHEN NAME = 'A' THEN 1 ELSE 0 END) AS aCount, "
        + " SUM(CASE WHEN NAME = 'B' THEN 1 ELSE 0 END) AS bCount, "
        + " SUM(CASE WHEN NAME = 'C' THEN 1 ELSE 0 END) AS cCount, "
        + " SUM(CASE WHEN NAME = 'D' THEN 1 ELSE 0 END) AS dCount, "
        + " SUM(CASE WHEN NAME = 'E' THEN 1 ELSE 0 END) AS eCount"
        + " FROM TB_ITEM GROUP BY NAME", nativeQuery = true)
    List<Object[]> findItemNameGroupUsingObjectArray();
}