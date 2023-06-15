package action.in.blog.proxy;

import action.in.blog.domain.Author;
import action.in.blog.domain.Book;
import action.in.blog.wsdl.GetAuthorsRequest;
import action.in.blog.wsdl.GetAuthorsResponse;
import action.in.blog.wsdl.GetBooksRequest;
import action.in.blog.wsdl.GetBooksResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.List;

@Component
public class DefaultBookStoreProxy extends WebServiceGatewaySupport implements BookStoreProxy {

    public DefaultBookStoreProxy(Jaxb2Marshaller marshaller) {
        setDefaultUri("http://provider-service:8080/ws");
        setMarshaller(marshaller);
        setUnmarshaller(marshaller);
    }

    @Override
    public List<Book> getBooks() {
        var request = new GetBooksRequest();
        var webServiceTemplate = getWebServiceTemplate();
        GetBooksResponse bookResponse = (GetBooksResponse) webServiceTemplate.marshalSendAndReceive(
                request,
                new SoapActionCallback(
                        "http://blog.in.action/book/getBooksRequest"
                )
        );
        return bookResponse.getBooks()
                .stream()
                .map(Book::of)
                .toList();
    }

    @Override
    public List<Author> getAuthors() {
        var request = new GetAuthorsRequest();
        var webServiceTemplate = getWebServiceTemplate();
        GetAuthorsResponse authorsResponse = (GetAuthorsResponse) webServiceTemplate.marshalSendAndReceive(
                request,
                new SoapActionCallback(
                        "http://blog.in.action/author/getAuthorsRequest"
                )
        );
        return authorsResponse.getAuthors()
                .stream()
                .map(Author::of)
                .toList();
    }
}
