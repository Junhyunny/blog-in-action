package action.in.blog.controller;

import action.in.blog.client.PostEventClient;
import action.in.blog.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostEventClient postEventClient;

    @PostMapping("/post")
    public void createPost(@RequestBody Post post) {
        postEventClient.pushPostCreationMessage(post.getUserId());
    }

    @PutMapping("/post")
    public void updatePost(@RequestBody Post post) {
        postEventClient.pushPostUpdateMessage(post.getUserId());
    }
}
