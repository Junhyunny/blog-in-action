package action.in.blog.endpoint;

import action.in.blog.book.Book;
import action.in.blog.book.Genre;
import action.in.blog.book.GetBooksRequest;
import action.in.blog.book.GetBooksResponse;
import com.github.javafaker.Faker;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.UUID;
import java.util.stream.IntStream;

@Endpoint
public class BookEndPoint {

    private static final String NAMESPACE_URI = "http://blog.in.action/book";

    private Genre getGenre(int number) {
        return switch (number % 5) {
            case 1 -> Genre.POEM;
            case 2 -> Genre.NOVEL;
            case 3 -> Genre.PLAY;
            case 4 -> Genre.LITERATURE;
            default -> Genre.ESSAY;
        };
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBooksRequest")
    @ResponsePayload
    public GetBooksResponse getBooks(@RequestPayload GetBooksRequest request) {
        var faker = new Faker();
        var response = new GetBooksResponse();
        response.getBooks().addAll(
                IntStream.range(1, 10)
                        .mapToObj(number -> {
                            var book = new Book();
                            book.setId(number);
                            book.setAuthor(number);
                            book.setAuthorPenName(faker.book().author());
                            book.setName(faker.book().title());
                            book.setGenre(getGenre(number));
                            book.setIsbn(UUID.randomUUID().toString());
                            book.setPrice(faker.random().nextInt(10000, 30000));
                            return book;
                        }).toList()
        );
        return response;
    }
}
