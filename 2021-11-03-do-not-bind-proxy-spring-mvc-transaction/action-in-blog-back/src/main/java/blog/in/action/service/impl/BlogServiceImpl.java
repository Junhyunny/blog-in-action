package blog.in.action.service.impl;

import blog.in.action.dao.BlogDao;
import blog.in.action.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Override
    public void updateBlog() {
        blogDao.updateBlog();
    }

    @Override
    public void rollbackAfterException() {
        blogDao.updateBlog();
        if (true) {
            new RuntimeException("occur exception");
        }
    }
}
