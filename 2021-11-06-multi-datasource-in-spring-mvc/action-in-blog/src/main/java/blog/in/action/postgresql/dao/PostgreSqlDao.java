package blog.in.action.postgresql.dao;

import java.util.List;
import java.util.Map;

public interface PostgreSqlDao {

    List<Map<String, Object>> selectTest();
}
