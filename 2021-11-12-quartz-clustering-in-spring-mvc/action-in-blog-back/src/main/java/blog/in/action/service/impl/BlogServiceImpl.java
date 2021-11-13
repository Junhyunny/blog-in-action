package blog.in.action.service.impl;

import blog.in.action.dao.BlogDao;
import blog.in.action.service.BlogService;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service("blobService")
public class BlogServiceImpl implements BlogService {

    private Logger logger = Logger.getLogger(BlogServiceImpl.class.getName());

    private final BlogDao blogDao;

    public BlogServiceImpl(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    @Override
    public void updateTest() {
        logger.info("update test table");
        List<Map<String, Object>> itemList = blogDao.selectTest();
        for (Map<String, Object> item : itemList) {
            blogDao.updateTest(item);
        }
    }
}
