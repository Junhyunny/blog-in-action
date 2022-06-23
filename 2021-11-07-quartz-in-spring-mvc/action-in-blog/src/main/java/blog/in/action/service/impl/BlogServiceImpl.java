package blog.in.action.service.impl;

import blog.in.action.dao.BlogDao;
import blog.in.action.service.BlogService;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service("blobService")
public class BlogServiceImpl implements BlogService {

    private final BlogDao blogDao;

    public BlogServiceImpl(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    @Override
    public void updateTest() {
        List<Map<String, Object>> itemList = blogDao.selectTest();
        for (Map<String, Object> item : itemList) {
            blogDao.updateTest(item);
            if (new Random().nextBoolean()) {
                throw new RuntimeException("throw exception");
            }
        }
    }
}
