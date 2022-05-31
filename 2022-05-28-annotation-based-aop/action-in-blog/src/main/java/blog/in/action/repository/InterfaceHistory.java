package blog.in.action.repository;

import blog.in.action.converter.StringArrayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class InterfaceHistory {

    @Id
    @GeneratedValue
    private long id;
    private String serviceId;
    @Convert(converter = StringArrayConverter.class)
    private String[] path;
    private String explainText;
    private String response;
    private Timestamp requestTime;
    private Timestamp responseTime;
}
