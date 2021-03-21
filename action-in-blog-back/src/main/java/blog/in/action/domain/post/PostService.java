package blog.in.action.domain.post;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.in.action.domain.member.Member;
import lombok.extern.log4j.Log4j2;

@Log4j2
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

	@Transactional
	public void test(Long postId, int waitingTime) throws InterruptedException {
		long start = System.currentTimeMillis();
		Optional<Post> option = repo.findByIdForUpdate(postId);
		if (!option.isPresent()) {
			throw new RuntimeException("POST를 찾을 수 없습니다.");
		}
		log.info("포스트 조회에 걸린 시간: " + (System.currentTimeMillis() - start) + "ms");
		Post post = option.get();
		post.setPostContents("JPA는 어떤 방식으로 Pessimitic Lock을 제공하는지 정리하였습니다. " + Thread.currentThread().getName() + "에 의해 업데이트되었습니다.");
		updatePost(post);
		log.info(waitingTime + "ms 동안 대기합니다.");
		Thread.sleep(waitingTime);
	}
}
