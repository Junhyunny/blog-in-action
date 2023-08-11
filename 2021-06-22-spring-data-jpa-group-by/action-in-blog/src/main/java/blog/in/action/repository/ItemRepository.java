package blog.in.action.repository;

import blog.in.action.domain.ItemEntity;
import blog.in.action.domain.ItemNameCountProjection;
import blog.in.action.domain.ItemNameCountVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    @Query(value = """
            SELECT SUM(CASE WHEN item.name = 'A' THEN 1 ELSE 0 END) AS aCount,
                SUM(CASE WHEN item.name = 'B' THEN 1 ELSE 0 END) AS bCount,
                SUM(CASE WHEN item.name = 'C' THEN 1 ELSE 0 END) AS cCount,
                SUM(CASE WHEN item.name = 'D' THEN 1 ELSE 0 END) AS dCount
            FROM ItemEntity item GROUP BY item.name
            """)
    List<ItemNameCountVO> findEachCountGroupByItemName();

    @Query(value = """
            SELECT SUM(CASE WHEN item.name = 'A' THEN 1 ELSE 0 END) AS aCount,
                SUM(CASE WHEN item.name = 'B' THEN 1 ELSE 0 END) AS bCount,
                SUM(CASE WHEN item.name = 'C' THEN 1 ELSE 0 END) AS cCount,
                SUM(CASE WHEN item.name = 'D' THEN 1 ELSE 0 END) AS dCount
            FROM ItemEntity item GROUP BY item.name
            """)
    List<Object[]> findEachCountGroupByItemNameWithObjectArray();

    @Query(value = """
            SELECT new blog.in.action.domain.ItemNameCountVO(
               SUM(CASE WHEN item.name = 'A' THEN 1 ELSE 0 END),
               SUM(CASE WHEN item.name = 'B' THEN 1 ELSE 0 END),
               SUM(CASE WHEN item.name = 'C' THEN 1 ELSE 0 END),
               SUM(CASE WHEN item.name = 'D' THEN 1 ELSE 0 END)
            ) FROM ItemEntity item GROUP BY item.name
            """)
    List<ItemNameCountVO> findEachCountGroupByItemNameWithCustomClass();

    @Query(value = """
            SELECT SUM(CASE WHEN item.name = 'A' THEN 1 ELSE 0 END) AS aCount,
               SUM(CASE WHEN item.name = 'B' THEN 1 ELSE 0 END) AS bCount,
               SUM(CASE WHEN item.name = 'C' THEN 1 ELSE 0 END) AS cCount,
               SUM(CASE WHEN item.name = 'D' THEN 1 ELSE 0 END) AS dCount
            FROM TB_ITEM item GROUP BY item.name
            """, nativeQuery = true)
    List<ItemNameCountProjection> findEachCountGroupByItemNameWithProjection();
}
