package blog.in.action.repository;

import blog.in.action.converter.StringArrayConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private Timestamp requestTime;
    private Timestamp responseTime;
}
