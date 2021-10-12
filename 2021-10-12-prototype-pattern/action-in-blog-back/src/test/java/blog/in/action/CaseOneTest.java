package blog.in.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

@SpringBootTest
public class CaseOneTest {

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void beforeEach() {
        itemRepository.save(new Item("TEST_ITEM"));
    }

    @Test
    public void test_speed_whenUsingClone() throws CloneNotSupportedException {
        long start = System.currentTimeMillis();
        Optional<Item> optional = itemRepository.findFirstByName("TEST_ITEM");
        if (optional.isEmpty()) {
            return;
        }
        Item item = optional.get();
        List<Item> list = new ArrayList<>();
        for (int index = 0; index < 10000; index++) {
            list.add(item.clone());
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test_speed_whenUsingSelectManyTimes() {
        long start = System.currentTimeMillis();
        List<Item> list = new ArrayList<>();
        for (int index = 0; index < 10000; index++) {
            Optional<Item> optional = itemRepository.findFirstByName("TEST_ITEM");
            if (optional.isEmpty()) {
                return;
            }
            list.add(optional.get());
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_ITEM")
class Item implements Cloneable {

    public Item(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Override
    protected Item clone() throws CloneNotSupportedException {
        return (Item) super.clone();
    }
}

interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findFirstByName(String name);
}