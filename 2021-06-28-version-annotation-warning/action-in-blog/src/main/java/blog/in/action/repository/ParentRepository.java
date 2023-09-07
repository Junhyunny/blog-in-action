package blog.in.action.repository;

import blog.in.action.domain.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<ParentEntity, Long> {
}
