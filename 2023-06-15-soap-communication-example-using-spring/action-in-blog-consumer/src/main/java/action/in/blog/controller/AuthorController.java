package action.in.blog.controller;

import action.in.blog.domain.Author;
import action.in.blog.proxy.BookStoreProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorController {

    private final BookStoreProxy bookStoreProxy;

    public AuthorController(BookStoreProxy bookStoreProxy) {
        this.bookStoreProxy = bookStoreProxy;
    }

    @GetMapping("/authors")
    public List<Author> getAuthors() {
        return bookStoreProxy.getAuthors();
    }
}
