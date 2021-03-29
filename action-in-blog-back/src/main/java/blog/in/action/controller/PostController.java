package blog.in.action.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import blog.in.action.domain.post.PostService;

@Controller
@RequestMapping(value = "/api/post")
public class PostController {

	private final PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	public void method() {
		// 불가능 컴파일 에러
		// this.postService = null;
		this.postService.findById(1L);
	}
}
