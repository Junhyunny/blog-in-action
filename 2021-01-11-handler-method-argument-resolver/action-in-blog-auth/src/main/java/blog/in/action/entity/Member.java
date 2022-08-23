package blog.in.action.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import blog.in.action.converter.StringListConverter;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "TB_MEMBER")
public class Member {

	@Id
	private String id;

	@Column
	private String password;

	@Column
	@Convert(converter = StringListConverter.class)
	private List<String> authorities;
}
