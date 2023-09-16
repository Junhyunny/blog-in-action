package action.in.blog.service;

import action.in.blog.domain.Post;

import java.util.List;

public interface PostService {

    List<Post> getPosts();

    void createPost(Post post);
}
