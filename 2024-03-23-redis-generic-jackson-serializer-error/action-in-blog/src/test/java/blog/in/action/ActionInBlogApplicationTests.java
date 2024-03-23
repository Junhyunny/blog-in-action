package blog.in.action;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.SerializationException;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertThrows;

@Testcontainers
@SpringBootTest
class ActionInBlogApplicationTests {

    @Container
    @ServiceConnection
    static GenericContainer<?> redisContainer = new GenericContainer<>(DockerImageName.parse("redis:latest")).withExposedPorts(6379);

    @Autowired
    RedisTemplate<String, Object> sut;

    @Test
    void setString() {
        var KEY = "STRING";
        sut.opsForValue().set(KEY, "Hello");


        var result = sut.opsForValue().get(KEY);
        Assertions.assertEquals("Hello", result);
    }

    @Test
    void setInteger() {
        var KEY = "INTEGER";
        sut.opsForValue().set(KEY, 1);


        var result = sut.opsForValue().get(KEY);
        Assertions.assertEquals(1, result);
    }

    @Test
    void setArrList() {
        var KEY = "ARRAY_LIST";
        var list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        sut.opsForValue().set(KEY, list);


        var result = sut.opsForValue().get(KEY);
        Assertions.assertEquals(list, result);
    }

    @Test
    void setHashSet() {
        var KEY = "HASH_SET";
        var set = new HashSet<>();
        set.add(1);
        set.add(2);
        set.add(3);
        sut.opsForValue().set(KEY, set);


        var result = sut.opsForValue().get(KEY);
        Assertions.assertEquals(set, result);
    }

    @Test
    void setObject() {
        var KEY = "OBJECT";
        sut.opsForValue().set(KEY, new Item(1, "Hello"));


        var result = sut.opsForValue().get(KEY);
        Assertions.assertEquals(new Item(1, "Hello"), result);
    }

    @Test
    void setListFromStream_throwsSerializationException() {
        var KEY = "LIST_FROM_STREAM";
        var list = new ArrayList<Item>();
        list.add(new Item(1, "Hello"));
        list.add(new Item(2, "World"));
        sut.opsForValue().set(
                KEY,
                list.stream()
                        .map(Item::id)
                        .toList()
        );


        var throwable = assertThrows(SerializationException.class, () -> sut.opsForValue().get(KEY));
        System.out.println(throwable.getMessage());
    }

    @Test
    void setArray_throwsSerializationException() {
        var KEY = "ARRAY";
        sut.opsForValue().set(KEY, new int[]{1, 2, 3, 4, 5});


        var throwable = assertThrows(SerializationException.class, () -> sut.opsForValue().get(KEY));
        System.out.println(throwable.getMessage());
    }

    @Test
    void setIntegerList_throwsSerializationException() {
        var KEY = "INTEGER_LIST";
        sut.opsForValue().set(KEY, List.of(1, 2, 3, 4, 5));


        var throwable = assertThrows(SerializationException.class, () -> sut.opsForValue().get(KEY));
        System.out.println(throwable.getMessage());
    }

    @Test
    void setItemList_throwsSerializationException() {
        var KEY = "ITEM_LIST";
        sut.opsForValue().set(KEY, List.of(new Item(1, "Hello"), new Item(2, "World")));


        var throwable = assertThrows(SerializationException.class, () -> sut.opsForValue().get(KEY));
        System.out.println(throwable.getMessage());
    }

    @Test
    void setWrappedList() {
        var KEY = "WRAPPED_LIST";
        sut.opsForValue().set(KEY, new WrappedList<>(List.of(1, 2, 3, 4, 5)));


        var result = sut.opsForValue().get(KEY);
        Assertions.assertEquals(new WrappedList<>(List.of(1, 2, 3, 4, 5)), result);
    }

    @Test
    void setIntegerArrayList() {
        var KEY = "INTEGER_ARRAYLIST";
        sut.opsForValue().set(KEY, new ArrayList<>(List.of(1, 2, 3, 4, 5)));


        var result = sut.opsForValue().get(KEY);
        Assertions.assertEquals(new ArrayList<>(List.of(1, 2, 3, 4, 5)), result);
    }

    @Test
    void setWrappedItemList() {
        var KEY = "WRAPPED_ITEM_LIST";
        sut.opsForValue().set(
                KEY,
                new WrappedList<>(
                        List.of(new Item(1, "Hello"), new Item(2, "World"))
                )
        );


        var result = sut.opsForValue().get(KEY);
        Assertions.assertEquals(
                new WrappedList<>(
                        List.of(new Item(1, "Hello"), new Item(2, "World"))
                ),
                result
        );
    }

    @Test
    void setWrappedArray() {
        var KEY = "WRAPPED_ARRAY";
        sut.opsForValue().set(KEY, new WrappedArray(new int[]{1, 2, 3, 4, 5}));


        var result = (WrappedArray) sut.opsForValue().get(KEY);
        assert result != null;
        Assertions.assertArrayEquals(new int[]{1, 2, 3, 4, 5}, result.items());
    }
}

record Item(int id, String value) {
}

record WrappedList<T>(List<T> items) {
}

record WrappedArray(int[] items) {
}