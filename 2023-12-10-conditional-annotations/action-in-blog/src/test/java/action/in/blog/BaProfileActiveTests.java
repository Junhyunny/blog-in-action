package action.in.blog;

import action.in.blog.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        properties = "property.ba-components=enabled"
)
class BaProfileActiveTests {

    @Autowired(required = false)
    BarFooService barFooService;

    @Autowired(required = false)
    BarService barService;

    @Autowired(required = false)
    BazService bazService;

    @Autowired(required = false)
    FooBarService foobarService;

    @Autowired(required = false)
    FooService fooService;

    @Test
    void contextLoads() {

        assertNull(barFooService);
        assertNotNull(barService);
        assertNotNull(bazService);
        assertNotNull(foobarService);
        assertNotNull(fooService);
        assertEquals("foo.disabled", fooService.getName());
    }
}
