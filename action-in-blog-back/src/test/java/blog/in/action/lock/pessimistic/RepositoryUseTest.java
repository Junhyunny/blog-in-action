package blog.in.action.lock.pessimistic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import blog.in.action.domain.member.Member;
import blog.in.action.domain.post.Post;
import blog.in.action.domain.post.PostService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class RepositoryUseTest {

	@Autowired
	private PostService postService;

	@BeforeEach
	private void beforeEach() {
		Page<Post> page = postService.findByTitlePost("Pessimitic Lock", PageRequest.of(0, 10, Sort.by(Direction.DESC, "postTitle")));
		if (page.isEmpty()) {
			Post post = new Post(new Member("01012341234"));
			post.setPostTitle("Pessimitic Lock");
			post.setPostContents("JPA는 어떤 방식으로 Pessimitic Lock을 제공하는지 정리하였습니다.");
			post.setVersionNo(Long.valueOf(0L));
			postService.registPost(post);
		} else {
			Post post = page.getContent().get(0);
			post.setPostContents("JPA는 어떤 방식으로 Pessimitic Lock을 제공하는지 정리하였습니다.");
			postService.updatePost(post);
		}
	}

	@Test
	public void test() {
		Page<Post> page = postService.findByTitlePost("Pessimitic Lock", PageRequest.of(0, 10, Sort.by(Direction.DESC, "postTitle")));
		if (!page.isEmpty()) {
			Post post = page.getContent().get(0);
			Thread tx1 = new Thread(new UpdatePostTask(post.getId(), 1500));
			Thread tx2 = new Thread(new UpdatePostTask(post.getId(), 2000));
			tx1.setName("1.5 초 대기 스레드");
			tx2.setName("2.0 초 대기 스레드");
			tx1.start();
			tx2.start();
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				log.error("main thread sleep error", e);
			}
		}
	}

	private class UpdatePostTask implements Runnable {

		private Long postId;

		private Integer waitingTime;

		public UpdatePostTask(Long postId, Integer waitingTime) {
			this.postId = postId;
			this.waitingTime = waitingTime;
		}

		@Override
		public void run() {
			try {
				postService.test(postId, waitingTime);
			} catch (Exception e) {
				log.error("update thread sleep error", e);
			}
		}
	}
}