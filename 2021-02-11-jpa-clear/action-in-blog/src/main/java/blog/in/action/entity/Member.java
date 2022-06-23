package blog.in.action.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import blog.in.action.converter.StringListConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_MEMBER")
public class Member {

	@Id
	private String id;

	@Column
	private String password;

	@Column
	@Convert(converter = StringListConverter.class)
	private List<String> authorities;

	@Column
	private String memberName;

	@Column
	private String memberEmail;
}
