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
import lombok.AllArgsConstructor;
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
    public void test_throwException_usingClass() {
        assertThrows(ConverterNotFoundException.class, () -> itemRepository.findItemNameGroupUsingClass());
    }

    @Test
    public void test_doesNotThrowException_usingObjectArray() {
        assertDoesNotThrow(() -> itemRepository.findItemNameGroupUsingObjectArray());
        itemRepository.findItemNameGroupUsingObjectArray().stream().forEach(objects -> {
            StringBuffer buffer = new StringBuffer("[");
            for (Object obj : objects) {
                buffer.append(obj).append(", ");
            }
            log.info(buffer.append("]"));
        });
    }

    @Test
    public void test_getResult_usingClassWithJpql() {
        itemRepository.findItemNameGroupUsingClassWithJpql().stream().forEach(itemNameGroupVo -> {
            StringBuffer buffer = new StringBuffer("[");
            buffer.append(itemNameGroupVo).append(", ");
            log.info(buffer.append("]"));
        });
    }

    @Test
    public void test_getResult_usingInterfaceWithNative() {
        assertDoesNotThrow(() -> itemRepository.findItemNameGroupUsingInterfaceWithNative());
        itemRepository.findItemNameGroupUsingInterfaceWithNative().stream().forEach(itemNameGroup -> {
            StringBuffer buffer = new StringBuffer("[");
            buffer.append(itemNameGroup.getACount()).append(", ");
            buffer.append(itemNameGroup.getBCount()).append(", ");
            buffer.append(itemNameGroup.getCCount()).append(", ");
            buffer.append(itemNameGroup.getDCount()).append(", ");
            buffer.append(itemNameGroup.getECount()).append("");
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
@AllArgsConstructor
@ToString
class ItemNameGroupVo {

    long aCount;
    long bCount;
    long cCount;
    long dCount;
    long eCount;
}

interface ItemNameGroup {

    long getACount();

    long getBCount();

    long getCCount();

    long getDCount();

    long getECount();
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

    @Query("SELECT new blog.in.action.groupby.ItemNameGroupVo("
        + " SUM(CASE WHEN i.name = 'A' THEN 1 ELSE 0 END), "
        + " SUM(CASE WHEN i.name = 'B' THEN 1 ELSE 0 END), "
        + " SUM(CASE WHEN i.name = 'C' THEN 1 ELSE 0 END), "
        + " SUM(CASE WHEN i.name = 'D' THEN 1 ELSE 0 END), "
        + " SUM(CASE WHEN i.name = 'E' THEN 1 ELSE 0 END)) "
        + " FROM Item i GROUP BY i.name")
    List<ItemNameGroupVo> findItemNameGroupUsingClassWithJpql();

    @Query(value = "SELECT SUM(CASE WHEN NAME = 'A' THEN 1 ELSE 0 END) AS aCount, "
        + " SUM(CASE WHEN NAME = 'B' THEN 1 ELSE 0 END) AS bCount, "
        + " SUM(CASE WHEN NAME = 'C' THEN 1 ELSE 0 END) AS cCount, "
        + " SUM(CASE WHEN NAME = 'D' THEN 1 ELSE 0 END) AS dCount, "
        + " SUM(CASE WHEN NAME = 'E' THEN 1 ELSE 0 END) AS eCount"
        + " FROM TB_ITEM GROUP BY NAME", nativeQuery = true)
    List<ItemNameGroup> findItemNameGroupUsingInterfaceWithNative();
}