package action.in.blog.endpoint;

import action.in.blog.author.Author;
import action.in.blog.author.GetAuthorsRequest;
import action.in.blog.author.GetAuthorsResponse;
import action.in.blog.author.Sex;
import com.github.javafaker.Faker;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.stream.IntStream;

@Endpoint // RestController
public class AuthorEndPoint {

    private static final String NAMESPACE_URI = "http://blog.in.action/author";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAuthorsRequest") // RequestMapping
    @ResponsePayload // ResponseBody
    public GetAuthorsResponse getAuthors(@RequestPayload GetAuthorsRequest request) {
        var faker = new Faker();
        var response = new GetAuthorsResponse();
        response.getAuthors().addAll(
                IntStream.range(1, 10)
                        .mapToObj(number -> {
                            var author = new Author();
                            author.setId(number);
                            author.setPenName(faker.name().username());
                            author.setName(faker.name().fullName());
                            author.setSex(number % 2 == 0 ? Sex.MALE : Sex.FEMALE);
                            author.setEmail(faker.internet().emailAddress());
                            author.setContact(faker.phoneNumber().phoneNumber());
                            return author;
                        }).toList()
        );
        return response;
    }
}