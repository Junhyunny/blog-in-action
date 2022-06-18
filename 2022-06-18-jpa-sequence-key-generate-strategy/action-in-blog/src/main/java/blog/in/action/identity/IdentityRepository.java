package blog.in.action.identity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityRepository extends JpaRepository<IdentityEntity, Long> {
}
