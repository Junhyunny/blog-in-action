package blog.in.action;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findFirstByName(String name);
}

@Slf4j
@DataJpaTest
public class SearchDataTests {

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void beforeEach() {
        itemRepository.save(new Item("Hello World"));
    }

    @Test
    void fetch_data_only_first_time() throws CloneNotSupportedException {
        long start = System.currentTimeMillis();
        Optional<Item> optional = itemRepository.findFirstByName("Hello World");
        Item item = optional.orElseThrow();
        List<Item> list = new ArrayList<>();
        for (int index = 0; index < 10000; index++) {
            list.add(item.clone());
        }
        assertThat(list.size(), equalTo(10000));
        log.info(String.format("total running time - %s", System.currentTimeMillis() - start));
    }

    @Test
    void fetch_data_every_times() {
        long start = System.currentTimeMillis();
        List<Item> list = new ArrayList<>();
        for (int index = 0; index < 10000; index++) {
            Optional<Item> optional = itemRepository.findFirstByName("Hello World");
            Item item = optional.orElseThrow();
            list.add(item);
        }
        assertThat(list.size(), equalTo(10000));
        log.info(String.format("total running time - %s", System.currentTimeMillis() - start));
    }
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
class Item implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public Item(String name) {
        this.name = name;
    }

    @Override
    protected Item clone() throws CloneNotSupportedException {
        return new Item(this.id, this.name);
    }
}