package blog.in.action.service;

import blog.in.action.entity.Post;
import blog.in.action.repository.PostRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public Page<Post> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
