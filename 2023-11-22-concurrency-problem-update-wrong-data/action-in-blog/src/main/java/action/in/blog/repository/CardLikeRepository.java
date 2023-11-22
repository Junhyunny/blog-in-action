package action.in.blog.repository;

import action.in.blog.domain.CardLikeEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CardLikeRepository extends JpaRepository<CardLikeEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select cl from CardLikeEntity cl where cl.cardId = :cardId")
    Optional<CardLikeEntity> findByIdWithLock(String cardId);
}
