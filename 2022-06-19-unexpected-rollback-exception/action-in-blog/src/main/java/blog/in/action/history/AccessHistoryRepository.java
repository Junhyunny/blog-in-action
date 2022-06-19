package blog.in.action.history;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessHistoryRepository extends JpaRepository<AccessHistoryEntity, Long> {
}
