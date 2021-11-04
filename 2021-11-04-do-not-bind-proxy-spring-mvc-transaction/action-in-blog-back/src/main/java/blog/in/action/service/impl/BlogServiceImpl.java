package blog.in.action.service.impl;

import blog.in.action.dao.BlogDao;
import blog.in.action.service.BlogService;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogDao blogDao;

    public BlogServiceImpl(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    @Override
    public void updateBlog() {
        blogDao.updateBlog();
    }

    @Override
    public void rollbackAfterException() {
        blogDao.updateBlog();
        if (true) {
            throw new RuntimeException("occur exception");
        }
    }
}
