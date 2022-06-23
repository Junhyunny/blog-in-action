package blog.in.action.mysql.service.impl;

import blog.in.action.mysql.dao.MySqlDao;
import blog.in.action.mysql.service.MySqlService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MySqlServiceImpl implements MySqlService {

    private final MySqlDao mySqlDao;

    public MySqlServiceImpl(MySqlDao mySqlDao) {
        this.mySqlDao = mySqlDao;
    }

    @Override
    public List<Map<String, Object>> selectTest() {
        return mySqlDao.selectTest();
    }
}
