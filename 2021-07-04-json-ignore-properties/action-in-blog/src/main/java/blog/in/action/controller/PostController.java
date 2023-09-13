package blog.in.action.controller;

import blog.in.action.domain.Post;
import blog.in.action.domain.Reply;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    @GetMapping("/posts")
    public List<Post> getPosts() {

        var post = Post.builder()
                .id(1L)
                .content("Hello World")
                .replies(new ArrayList<>())
                .build();

        var reply = Reply.builder()
                .id(1L)
                .content("This is reply")
                .post(post)
                .build();

        post.addReply(reply);

        return List.of(post);
    }
}
