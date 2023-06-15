package action.in.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Book {

    protected long id;
    protected String isbn;
    protected String name;
    protected long author;
    protected String authorPenName;
    protected int price;
    protected String genre;

    public static Book of(action.in.blog.wsdl.Book bookResponse) {
        return Book.builder()
                .id(bookResponse.getId())
                .isbn(bookResponse.getIsbn())
                .name(bookResponse.getName())
                .author(bookResponse.getAuthor())
                .authorPenName(bookResponse.getAuthorPenName())
                .price(bookResponse.getPrice())
                .genre(bookResponse.getGenre().value())
                .build();
    }
}
