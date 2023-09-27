package action.in.blog.service;

import action.in.blog.domain.Post;
import action.in.blog.interceptor.PostInterceptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostServiceTest {

    PostService sut;

    @BeforeEach
    void setUp() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DefaultPostService.class);
        enhancer.setCallback(new PostInterceptor());
        sut = (PostService) enhancer.create();
    }

    @Test
    void invoke_getPosts() {

        var result = sut.getPosts();
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

        sut.createPost(new Post(1, "Hello World", "This is new content."));
    }
}