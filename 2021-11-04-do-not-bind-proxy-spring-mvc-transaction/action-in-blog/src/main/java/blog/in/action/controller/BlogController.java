package blog.in.action.controller;

import blog.in.action.service.BlogService;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

    private Logger log = Logger.getLogger(BlogController.class.getName());

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        log.info("BlogController 생성자 주입: " + blogService);
        this.blogService = blogService;
    }

    @RequestMapping(value = {"", "/"})
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
