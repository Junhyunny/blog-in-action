package action.in.blog.join;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class CompanyEntity {

    @Id
    private String bizRegistrationNumber;
    private String name;
    private String providerId;
    private String consumerId;
}
