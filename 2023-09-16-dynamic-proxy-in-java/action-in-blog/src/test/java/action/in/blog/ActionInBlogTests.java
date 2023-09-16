package action.in.blog;

import action.in.blog.domain.Post;
import action.in.blog.handler.PostInvocationHandler;
import action.in.blog.service.DefaultPostService;
import action.in.blog.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class ActionInBlogTests {

    PostService proxy;

    @BeforeEach
    void setUp() {
        proxy = (PostService) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{PostService.class},
                new PostInvocationHandler(new DefaultPostService())
        );
    }

    @Test
    void invoke_getPosts() {

        var result = proxy.getPosts();
        var firstPost = result.get(0);
        var secondPost = result.get(1);


        assertEquals(2, result.size());
        assertEquals("Hello World", firstPost.title());
        assertEquals("This is content.", firstPost.content());
        assertEquals("Junhyunny's Devlog", secondPost.title());
        assertEquals("This is blog.", secondPost.content());
    }

    @Test
    void invoke_createPost() {

        var throwable = assertThrows(
                RuntimeException.class,
                () -> proxy.createPost(
                        new Post(1L, "Hello World", "This is content,")
                )
        );


        log.error(throwable.getMessage());
        assertEquals("createPost is not supported method", throwable.getMessage());
    }

    @Test
    void usingClass_throwException() {

        var throwable = assertThrows(RuntimeException.class, () -> {
            Proxy.newProxyInstance(
                    this.getClass().getClassLoader(),
                    new Class[]{DefaultPostService.class},
                    new PostInvocationHandler(new DefaultPostService())
            );
        });


        log.error(throwable.getMessage());
        assertEquals("action.in.blog.service.DefaultPostService is not an interface", throwable.getMessage());
    }
}
