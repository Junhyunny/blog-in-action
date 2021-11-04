package blog.in.action.controller;

import blog.in.action.service.BlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping("/update")
    public void updateBlog() {
        blogService.updateBlog();
    }

    @RequestMapping("/rollback")
    public void rollbackAfterException() {
        blogService.rollbackAfterException();
    }
}
