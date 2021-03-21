package blog.in.action.domain.post;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import blog.in.action.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "TB_POST")
public class Post {

	public Post(String postTitle) {
		this.postTitle = postTitle;
	}

	public Post(Member member) {
		this.postMember = member;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String postTitle;

	@Column
	private String postContents;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "POST_MEMBER", nullable = false)
	private Member postMember;

	// 2021-02-15, optimistic lock 매커니즘을 위한 컬럼 추가
	@Column
	// @Version
	private Long versionNo;
}
