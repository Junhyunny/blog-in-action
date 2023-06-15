//
// 이 파일은 JAXB의 Eclipse 구현에 의해 생성되었습니다 4.0.2 버전을 통해 생성되었습니다. 
// https://eclipse-ee4j.github.io/jaxb-ri를 참조하십시오. 
// 이 파일을 수정하면 소스 스키마를 재컴파일할 때 수정 사항이 손실됩니다. 
//


package action.in.blog.book;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the action.in.blog.book package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: action.in.blog.book
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetBooksRequest }
     * 
     * @return
     *     the new instance of {@link GetBooksRequest }
     */
    public GetBooksRequest createGetBooksRequest() {
        return new GetBooksRequest();
    }

    /**
     * Create an instance of {@link GetBooksResponse }
     * 
     * @return
     *     the new instance of {@link GetBooksResponse }
     */
    public GetBooksResponse createGetBooksResponse() {
        return new GetBooksResponse();
    }

    /**
     * Create an instance of {@link Book }
     * 
     * @return
     *     the new instance of {@link Book }
     */
    public Book createBook() {
        return new Book();
    }

}
