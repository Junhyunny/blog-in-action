package action.in.blog;

import action.in.blog.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        properties = "property.foo=enabled"
)
class FooProfileActiveTests {


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

        assertNotNull(barFooService);
        assertNull(barService);
        assertNull(bazService);
        assertNull(foobarService);
        assertNotNull(fooService);
        assertEquals("foo.enabled", fooService.getName());
    }
}
