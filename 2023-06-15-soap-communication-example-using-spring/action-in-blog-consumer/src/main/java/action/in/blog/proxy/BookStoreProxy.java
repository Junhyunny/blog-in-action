package action.in.blog.proxy;

import action.in.blog.domain.Author;
import action.in.blog.domain.Book;

import java.util.List;

public interface BookStoreProxy {

    List<Book> getBooks();

    List<Author> getAuthors();
}
