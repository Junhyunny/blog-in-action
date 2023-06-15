//
// 이 파일은 JAXB의 Eclipse 구현에 의해 생성되었습니다 4.0.2 버전을 통해 생성되었습니다. 
// https://eclipse-ee4j.github.io/jaxb-ri를 참조하십시오. 
// 이 파일을 수정하면 소스 스키마를 재컴파일할 때 수정 사항이 손실됩니다. 
//


package action.in.blog.book;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>book complex type에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * 
 * <pre>{@code
 * <complexType name="book">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="isbn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="author" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         <element name="authorPenName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         <element name="price" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         <element name="genre" type="{http://blog.in.action/book}genre"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "book", propOrder = {
    "id",
    "isbn",
    "name",
    "author",
    "authorPenName",
    "price",
    "genre"
})
public class Book {

    protected long id;
    @XmlElement(required = true)
    protected String isbn;
    @XmlElement(required = true)
    protected String name;
    protected long author;
    @XmlElement(required = true)
    protected String authorPenName;
    protected int price;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected Genre genre;

    /**
     * id 속성의 값을 가져옵니다.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * id 속성의 값을 설정합니다.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * isbn 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * isbn 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsbn(String value) {
        this.isbn = value;
    }

    /**
     * name 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * name 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * author 속성의 값을 가져옵니다.
     * 
     */
    public long getAuthor() {
        return author;
    }

    /**
     * author 속성의 값을 설정합니다.
     * 
     */
    public void setAuthor(long value) {
        this.author = value;
    }

    /**
     * authorPenName 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorPenName() {
        return authorPenName;
    }

    /**
     * authorPenName 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorPenName(String value) {
        this.authorPenName = value;
    }

    /**
     * price 속성의 값을 가져옵니다.
     * 
     */
    public int getPrice() {
        return price;
    }

    /**
     * price 속성의 값을 설정합니다.
     * 
     */
    public void setPrice(int value) {
        this.price = value;
    }

    /**
     * genre 속성의 값을 가져옵니다.
     * 
     * @return
     *     possible object is
     *     {@link Genre }
     *     
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * genre 속성의 값을 설정합니다.
     * 
     * @param value
     *     allowed object is
     *     {@link Genre }
     *     
     */
    public void setGenre(Genre value) {
        this.genre = value;
    }

}
