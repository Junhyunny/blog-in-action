package blog.in.action.repository;

import blog.in.action.domain.ChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository<ChildEntity, Long> {
}
