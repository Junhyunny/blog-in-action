package blog.in.action.controller;

import blog.in.action.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/update")
    public void updateBlog() {
        blogService.updateBlog();
    }

    @RequestMapping("/rollback")
    public void rollbackAfterException() {
        blogService.rollbackAfterException();
    }
}
