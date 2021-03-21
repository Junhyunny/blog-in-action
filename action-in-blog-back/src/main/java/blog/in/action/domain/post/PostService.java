package blog.in.action.domain.post;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import blog.in.action.domain.member.Member;

@Service
public class PostService {

	@Autowired
	private PostRepository repo;

	public Post registPost(Post entity) {
		return repo.save(entity);
	}

	public Post updatePost(Post entity) {
		return repo.save(entity);
	}

	public Post findById(Long id) {
		Optional<Post> option = repo.findById(id);
		if (!option.isPresent()) {
			throw new RuntimeException("POST를 찾을 수 없습니다.");
		}
		return option.get();
	}

	public Page<Post> findByMember(Member member, Pageable pageable) {
		Example<Post> example = Example.of(new Post(member));
		return repo.findAll(example, pageable);
	}

	public Page<Post> findByTitlePost(String postTitle, Pageable pageable) {
		Example<Post> example = Example.of(new Post(postTitle));
		return repo.findAll(example, pageable);
	}
}
