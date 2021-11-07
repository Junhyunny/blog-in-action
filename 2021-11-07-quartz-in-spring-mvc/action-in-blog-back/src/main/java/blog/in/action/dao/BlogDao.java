package blog.in.action.dao;

import java.util.List;
import java.util.Map;

public interface BlogDao {

    List<Map<String, Object>> selectTest();

    void updateTest(Map<String, Object> test);
}
