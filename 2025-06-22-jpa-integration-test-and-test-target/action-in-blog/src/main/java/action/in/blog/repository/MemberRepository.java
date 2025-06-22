package action.in.blog.repository;

import action.in.blog.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    @Query("""
            select e from MemberEntity e where e.email = :email and e.enable is not false
            """)
    Optional<MemberEntity> findEnableMemberByEmail(String email);
}
