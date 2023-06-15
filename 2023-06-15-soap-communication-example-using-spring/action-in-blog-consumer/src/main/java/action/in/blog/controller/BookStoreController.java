package action.in.blog.controller;

import action.in.blog.domain.Book;
import action.in.blog.proxy.BookStoreProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookStoreController {

    private final BookStoreProxy bookStoreProxy;

    public BookStoreController(BookStoreProxy bookStoreProxy) {
        this.bookStoreProxy = bookStoreProxy;
    }

    @GetMapping("/books")
    public List<Book> getTodos() {
        return bookStoreProxy.getBooks();
    }
}
