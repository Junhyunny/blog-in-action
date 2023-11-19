package action.in.blog.repository;

import action.in.blog.domain.CollectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;

public interface CollectRepository extends JpaRepository<CollectEntity, Long> {

    boolean existsByUserIdAndCardId(String userId, String cardId);
}
