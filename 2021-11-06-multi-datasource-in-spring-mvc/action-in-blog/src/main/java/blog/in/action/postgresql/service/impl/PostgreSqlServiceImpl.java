package blog.in.action.postgresql.service.impl;

import blog.in.action.postgresql.dao.PostgreSqlDao;
import blog.in.action.postgresql.service.PostgreSqlService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PostgreSqlServiceImpl implements PostgreSqlService {

    private final PostgreSqlDao postgreSqlDao;

    public PostgreSqlServiceImpl(PostgreSqlDao postgreSqlDao) {
        this.postgreSqlDao = postgreSqlDao;
    }

    @Override
    public List<Map<String, Object>> selectTest() {
        return postgreSqlDao.selectTest();
    }
}
