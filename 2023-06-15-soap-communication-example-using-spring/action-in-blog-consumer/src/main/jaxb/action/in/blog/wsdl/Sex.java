//
// 이 파일은 JAXB의 Eclipse 구현에 의해 생성되었습니다 4.0.2 버전을 통해 생성되었습니다. 
// https://eclipse-ee4j.github.io/jaxb-ri를 참조하십시오. 
// 이 파일을 수정하면 소스 스키마를 재컴파일할 때 수정 사항이 손실됩니다. 
//


package action.in.blog.wsdl;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>sex에 대한 Java 클래스입니다.
 * 
 * <p>다음 스키마 단편이 이 클래스에 포함되는 필요한 콘텐츠를 지정합니다.
 * <pre>{@code
 * <simpleType name="sex">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="MALE"/>
 *     <enumeration value="FEMALE"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "sex")
@XmlEnum
public enum Sex {

    MALE,
    FEMALE;

    public String value() {
        return name();
    }

    public static Sex fromValue(String v) {
        return valueOf(v);
    }

}
