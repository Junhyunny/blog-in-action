package blog.in.action.controller;

import blog.in.action.repository.Post;
import blog.in.action.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping(path = {"", "/"})
    public String index() {
        return "redirect:/posts";
    }

    @GetMapping(path = "/posts")
    public String posts(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "Posts";
    }

    @PostMapping(path = "post")
    public String post(Model model, Post post) {
        post.removeTag();
        postRepository.save(post);
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "Posts";
    }

    @GetMapping(path = "/post/{postId}")
    public String post(Model model, @PathVariable long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        model.addAttribute("post", post);
        return "PostDetail";
    }
}
