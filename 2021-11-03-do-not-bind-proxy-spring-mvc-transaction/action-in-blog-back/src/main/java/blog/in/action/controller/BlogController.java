package blog.in.action.controller;

import blog.in.action.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

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
